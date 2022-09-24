package com.game.dto;

import com.game.entity.Entity;
import com.game.entity.Profession;
import com.game.entity.Race;

import javax.persistence.*;

public class EntityDTO {
    private String name;
    private String title;

    @Enumerated(value = EnumType.STRING)
    private Race race;

    @Enumerated(value = EnumType.STRING)
    private Profession profession;

    private Long birthday;

    private Boolean banned;
    private Integer experience;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public Boolean isBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }


    public Entity convertToEntity() {
        Entity entity = new Entity();
        entity.setName(name);
        entity.setTitle(title);
        entity.setRace(race);
        entity.setProfession(profession);
        entity.setBirthday(birthday);
        entity.setBanned(banned);
        entity.setExperience(experience);

        return entity;
    }

    public boolean areAllImportantFieldsAreFilledIn() {
        return  name  == null ||
                title == null ||
                race  == null ||
                profession == null ||
                birthday   == null ||
                experience == null;
    }


    @Override
    public String toString() {
        return "[DTO]: "+ name +", "+ title +", "+ race +", "+ profession +", "+ birthday +", "+ banned +", "+ experience;
    }
}
