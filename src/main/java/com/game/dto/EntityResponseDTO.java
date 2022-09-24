package com.game.dto;

import com.game.entity.Entity;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.Date;

public class EntityResponseDTO {
    private Long id;
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Long birthday;
    private Boolean banned;
    private Integer experience;
    private Integer level;
    private Integer untilNextLevel;

    public EntityResponseDTO() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    public Boolean isBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public static EntityResponseDTO getObjectFromEntity(Entity entity) {
        EntityResponseDTO dto = new EntityResponseDTO();
        dto.setName(entity.getName());
        dto.setTitle(entity.getTitle());
        dto.setRace(entity.getRace());
        dto.setProfession(entity.getProfession());
        dto.setBirthday(entity.getBirthday());
        dto.setBanned(entity.isBanned());
        dto.setExperience(entity.getExperience());
        dto.setId(entity.getId());
        dto.setLevel(entity.getLevel());
        dto.setUntilNextLevel(entity.getUntilNextLevel());

        return dto;
    }


    @Override
    public String toString() {
        return "[entity]: id=" + id + ", name=" + name + ", date=" + birthday;
    }
}
