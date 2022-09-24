package com.game.service;

import com.game.dto.EntityDTO;
import com.game.entity.Entity;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.EntityCriteriaRepository;
import com.game.repository.EntityRepository;
import com.game.util.Calculate;
import com.game.util.filter.EntitySearchCriteria;
import com.game.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional(readOnly = false)
public class EntityService {
    private static final String ENTITY_NOT_FOUND = "Сущность с указанным ID не найдена, ID =";

    private final EntityRepository repository;
    private final EntityCriteriaRepository criteriaRepository;
    @Autowired
    public EntityService(EntityRepository repository,
                         EntityCriteriaRepository criteriaRepository) {
        this.repository = repository;
        this.criteriaRepository = criteriaRepository;
    }

    public List<Entity> findAll(Map<String, String> filterMap) {
        // фильтры
        EntitySearchCriteria criteria = buildCriteria(filterMap);

        return criteriaRepository.findAllWithFiltersAndPaging(criteria);
    }

    public Entity findOne(long id) {
        return repository.findById(id).orElse(null);
    }

    public Entity save(Entity entity) {
        fixBanned(entity); // ban == null ? -> ban=false

        determineTheLevelAndBalanceFor(entity);

        return repository.save(entity);
    }



    public boolean delete(long id) {

        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;

        } else
            return false;
    }

    public int count(Map<String, String> filtersMap) {
        // фильтры
        EntitySearchCriteria entitySearchCriteria = buildCriteria(filtersMap);

        return (int) criteriaRepository.count(entitySearchCriteria);
    }

    public int count() {
        return (int) repository.count();
    }

    public Entity update(long id, EntityDTO entityDTO) {
        Entity entity = repository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException(ENTITY_NOT_FOUND + id) );

        assignFields(entityDTO, entity);
        determineTheLevelAndBalanceFor(entity);

        Entity updated = repository.save(entity);

        return updated;
    }




    private static void assignFields(EntityDTO entityDTO, Entity entity) {
        String name = entityDTO.getName(),
               title = entityDTO.getTitle();

        Race       race = entityDTO.getRace();
        Profession prof = entityDTO.getProfession();
        Boolean  banned = entityDTO.isBanned();
        Integer     exp = entityDTO.getExperience();
        Long   birthday = entityDTO.getBirthday();

        if (Objects.nonNull(name))  entity.setName(name);
        if (Objects.nonNull(title)) entity.setTitle(title);

        if (Objects.nonNull(race)) entity.setRace(race);
        if (Objects.nonNull(prof)) entity.setProfession(prof);

        if (Objects.nonNull(exp))      entity.setExperience(exp);
        if (Objects.nonNull(birthday)) entity.setBirthday(birthday);

        //  Если в запросе на создание игрока нет параметра “banned”, то “false”.
        if (Objects.nonNull(banned))   entity.setBanned(banned);
        else entity.setBanned(false);


        if (Objects.nonNull(exp)) {
            int level = Calculate.levelFromExp(exp);
            int untilNextLevel = Calculate.untilNextLevelFromLevelAndExp(level, exp);

            entity.setLevel(level);
            entity.setUntilNextLevel(untilNextLevel);
        }
    }

    private EntitySearchCriteria buildCriteria(Map<String, String> paramMap) {
        return new EntitySearchCriteria(paramMap);
    }

    private static void determineTheLevelAndBalanceFor(Entity entity) {
        Integer experience =  entity.getExperience();
        Integer level = Calculate.levelFromExp(experience);
        Integer untilNextLevel = Calculate.untilNextLevelFromLevelAndExp(level, experience);

        entity.setLevel(level);
        entity.setUntilNextLevel(untilNextLevel);
    }

    private static void fixBanned(Entity entity) {
        if (entity.isBanned() == null) {
            entity.setBanned(false);
        }
    }
}
