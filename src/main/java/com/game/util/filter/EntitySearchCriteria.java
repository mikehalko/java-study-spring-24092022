package com.game.util.filter;

import com.game.controller.PlayerOrder;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.Map;

public class EntitySearchCriteria {
    private String name;
    private String title;

    private Race race;
    private Profession profession;

    private Long after;
    private Long before;

    private Boolean banned;

    private Integer minExperience;
    private Integer maxExperience;

    private Integer minLevel;
    private Integer maxLevel;

    private PlayerOrder order;

    private Integer pageNumber;
    private Integer pageSize;

    public EntitySearchCriteria(Map<String, String> paramMap) {
        initFields(paramMap);
    }

    private void initFields(Map<String, String> map) {
        String   race = map.get("race"),
           profession = map.get("profession"),
                after = map.get("after"),
               before = map.get("before"),
               banned = map.get("banned"),
               minExp = map.get("minExperience"),
               maxExp = map.get("maxExperience"),
               minLvl = map.get("minLevel"),
               maxLvl = map.get("maxLevel"),
                order = map.get("order"),
           pageNumber = map.get("pageNumber"),
             pageSize = map.get("pageSize");

        this.name   = map.get("name");
        this.title  = map.get("title");

        this.race       =     (race == null)    ?    null : Race.valueOf(race);
        this.profession =  (profession == null) ?    null : Profession.valueOf(profession);

        this.after  =        (after == null)    ?    null : Long.valueOf(after);
        this.before =       (before == null)    ?    null : Long.valueOf(before);

        this.banned =        (banned == null)   ?    null : Boolean.valueOf(banned);

        this.minExperience = (minExp == null)    ?    null : Integer.valueOf(minExp);
        this.maxExperience = (maxExp == null)    ?    null : Integer.valueOf(maxExp);

        this.minLevel =      (minLvl == null)    ?    null : Integer.valueOf(minLvl);
        this.maxLevel =      (maxLvl == null)    ?    null : Integer.valueOf(maxLvl);

        this.order    =       (order == null)    ?    null : PlayerOrder.valueOf(order);

        this.pageNumber =  (pageNumber == null)  ?    null : Integer.valueOf(pageNumber);
        this.pageSize   =   (pageSize == null)   ?    null : Integer.valueOf(pageSize);
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

    public Long getBefore() {
        return before;
    }

    public void setBefore(Long before) {
        this.before = before;
    }

    public Long getAfter() {
        return after;
    }

    public void setAfter(Long after) {
        this.after = after;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Integer getMinExperience() {
        return minExperience;
    }

    public void setMinExperience(Integer minExperience) {
        this.minExperience = minExperience;
    }

    public Integer getMaxExperience() {
        return maxExperience;
    }

    public void setMaxExperience(Integer maxExperience) {
        this.maxExperience = maxExperience;
    }

    public Integer getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(Integer minLevel) {
        this.minLevel = minLevel;
    }

    public Integer getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(Integer maxLevel) {
        this.maxLevel = maxLevel;
    }

    public PlayerOrder getOrder() {
        return order;
    }

    public void setOrder(PlayerOrder order) {
        this.order = order;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }


    @Override
    public String toString() {
        return "EntitySearchCriteria{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", race=" + race +
                ", profession=" + profession +
                ", after=" + after +
                ", before=" + before +
                ", banned=" + banned +
                ", minExperience=" + minExperience +
                ", maxExperience=" + maxExperience +
                ", minLevel=" + minLevel +
                ", maxLevel=" + maxLevel +
                ", order=" + order +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                '}';
    }
}