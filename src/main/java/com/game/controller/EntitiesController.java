package com.game.controller;

import com.game.dto.EntityDTO;
import com.game.dto.EntityResponseDTO;
import com.game.entity.Entity;
import com.game.exception.*;
import com.game.service.EntityService;
import com.game.util.EntityDTOValidator;
import com.game.util.EntityErrorResponse;
import com.game.util.EntityFieldError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/rest/players")
public class EntitiesController {
    private static final String ENTITY_NOT_FOUND = "Сущность с указанным ID не найдена, ID =";
    private static final String ID_NOT_VALID = "Указанный ID не является валидным, ID =";
    private static final String SOME_WRONG_SAVE = "Что-то пошло не так при сохранении сущности в БД";
    private static final String FIELD_EMPTY = "Не все поля в форме заполнены";

    private final EntityService service;
    private final EntityDTOValidator validator;
    @Autowired
    public EntitiesController(EntityService service, EntityDTOValidator validatorDTO) {
        this.service = service;
        this.validator = validatorDTO;
    }

    @GetMapping
    public List<Entity> index(@RequestParam Map<String, String> filtersParamMap) {
        System.out.println("INDEX");
        List<Entity> result = service.findAll(filtersParamMap);

        return result;
    }

    @PostMapping
    public EntityResponseDTO save(@RequestBody EntityDTO entityDTO) {
        System.out.println("SAVE");

        if (entityDTO.areAllImportantFieldsAreFilledIn()) {
            throw new EntityEmptyFieldsException(FIELD_EMPTY);
        }

        validate(entityDTO);
        Entity entity = entityDTO.convertToEntity();
        Entity saved = service.save(entity);

        if (Objects.isNull(saved)) {
            throw new EntitySomeException(SOME_WRONG_SAVE);
        }

        EntityResponseDTO dtoResponse = EntityResponseDTO.getObjectFromEntity(saved);

        return dtoResponse;
    }

    @GetMapping("/{id}")
    public Entity show(@PathVariable("id") Long id) {
        System.out.println("SHOW");

        if (!validator.validID(id)) {
            throw new EntityBadIDException(ID_NOT_VALID + id);
        }

        Entity responseEntity = service.findOne(id);


        if (Objects.isNull(responseEntity)) {
            throw new EntityNotFoundException(ENTITY_NOT_FOUND + id);
        }

        return responseEntity;
    }

    @PostMapping("/{id}")
    public Entity edit(@PathVariable("id") Long id,
                       @RequestBody EntityDTO entityDTO) {
        System.out.println("EDIT");

        if (!validator.validID(id)) {
            throw new EntityBadIDException(ID_NOT_VALID + id);
        }

        validate(entityDTO);

        Entity saved = service.update(id, entityDTO);

        return saved;
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable("id") Long id) {
        System.out.println("DELETE");


        if (!validator.validID(id)) {
            throw new EntityBadIDException(ID_NOT_VALID);
        }

        boolean deleted = service.delete(id);

        if (!deleted) {
            throw new EntityNotFoundException(ENTITY_NOT_FOUND + id);
        }

        return HttpStatus.OK;
    }

    @GetMapping("/count")
    public Integer count(@RequestParam Map<String, String> filtersParamMap) {
        System.out.println("COUNT");

        return service.count(filtersParamMap);
    }



    @ExceptionHandler
    public ResponseEntity<EntityErrorResponse> handleException(EntitySomeException e) {
        System.out.println("THROW EXCEPTION="+ e.getMessage());

        EntityErrorResponse errorResponse = new EntityErrorResponse();
        errorResponse.setErrorDescription(e.getMessage());
        errorResponse.setTimestamp(new Date().getTime());

        if (e instanceof EntityNotFoundException)
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

        if (e instanceof EntityBadIDException)
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        if (e instanceof EntityFieldErrorException)
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        if (e instanceof EntityEmptyFieldsException)
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);


        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    private void validate(@RequestBody EntityDTO entityDTO) {
        if (validator.hasErrors(entityDTO)) {
            StringBuilder message = new StringBuilder();
            for (EntityFieldError error: validator.getErrors()) {
                message.append(error.getFieldName()).append(" - ").append(error.getMessage()).append("\n");
            }

            throw new EntityFieldErrorException(message.toString());
        }
    }
}