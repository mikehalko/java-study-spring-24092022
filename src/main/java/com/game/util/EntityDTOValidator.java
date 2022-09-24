package com.game.util;

import com.game.dto.EntityDTO;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EntityDTOValidator {

    private static String FIELD_NAME_NAME = "name";
    private static String FIELD_NAME_TITLE = "title";
    private static String FIELD_NAME_BIRTHDAY = "birthday";
    private static String FIELD_NAME_EXPERIENCE = "experience";

    private static String WRONG_NAME = "длина имени не должна быть больше 12 или меньше 1, " +
                                            "не должно быть пустым (пробелы)";
    private static String WRONG_TITLE = "титул должен быть меньше 31 символа";
    private static String WRONG_BIRTHDAY = "Диапазон значений года 2000..3000 включительно";
    private static String WRONG_EXPERIENCE = "exp (> 0) and (<10000000000)";

    private static int NAME_MIN = 1;
    private static int NAME_MAX = 12;
    private static int TITLE_MIN = 1;
    private static int TITLE_MAX = 30;
    private static long BIRTHDAY_MIN = 946_684_800_000L;
    private static long BIRTHDAY_MAX = 32_503_662_000_000L;
    private static int EXPERIENCE_MIN = 0;
    private static int EXPERIENCE_MAX = 10_000_000;

    private List<EntityFieldError> errors;

    public boolean hasErrors(EntityDTO entity) {
        if (Objects.isNull(errors))
            errors = new LinkedList<>();
        else errors.clear();

        String name  = entity.getName();
        String title = entity.getTitle();
        Long birthday = entity.getBirthday();
        Integer exp = entity.getExperience();


        boolean nameIsBlank = false;
        if (name != null) {
            for (int i = 0; i < name.length(); i++) {
                if(name.charAt(i) == ' ') {
                    nameIsBlank = true;
                } else break;
            }
        }

        if ( (name != null) && ( (name.length() < NAME_MIN) || name.length() > TITLE_MAX || nameIsBlank) )
            errors.add(new EntityFieldError("name", WRONG_NAME));

        if ( (title != null) && ( title.length() < TITLE_MIN || title.length() > TITLE_MAX) )
            errors.add(new EntityFieldError("title", WRONG_TITLE));

        if ( (birthday != null) && (birthday < BIRTHDAY_MIN || birthday > BIRTHDAY_MAX) )
            errors.add(new EntityFieldError("birthday", WRONG_BIRTHDAY));

        if ( (exp != null) && (exp < EXPERIENCE_MIN || exp > EXPERIENCE_MAX) )
            errors.add(new EntityFieldError("experience", WRONG_EXPERIENCE));


        return !errors.isEmpty();
    }

    public boolean validID(Long id) {
        return id != null && id > 0;
    }

    public List<EntityFieldError> getErrors() {
        return errors;
    }
}