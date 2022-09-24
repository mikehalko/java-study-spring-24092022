package com.game.repository;

import com.game.controller.PlayerOrder;
import com.game.entity.Entity;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.util.filter.EntitySearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
@Transactional
public class EntityCriteriaRepository {
    private static final String DEFAULT_ORDER = PlayerOrder.ID.getFieldName();
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 3;

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    @Autowired
    public EntityCriteriaRepository(LocalContainerEntityManagerFactoryBean factoryBean) {
        this.entityManager = factoryBean.getObject().createEntityManager();
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public long count(EntitySearchCriteria entitySearchCriteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Entity> entityRoot = query.from(Entity.class);

        query.select(builder.count(entityRoot));

        // фильтрация
        Predicate predicate = getPredicate(entitySearchCriteria, entityRoot);
        query.where(predicate);

        // запрос
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);

        return typedQuery.getSingleResult();
    }

    public List<Entity> findAllWithFiltersAndPaging(EntitySearchCriteria entitySearchCriteria) {
        Integer pageNumber = entitySearchCriteria.getPageNumber();
        Integer pageSize = entitySearchCriteria.getPageSize();

        CriteriaQuery<Entity> query = criteriaBuilder.createQuery(Entity.class);
        Root<Entity> entityRoot = query.from(Entity.class);

        // фильтрация
        Predicate predicate = getPredicate(entitySearchCriteria, entityRoot);
        query.where(predicate);


        // Сортировка
        PlayerOrder order = entitySearchCriteria.getOrder();
        String sortField  = (order == null) ? DEFAULT_ORDER : order.getFieldName();
        query.orderBy(criteriaBuilder.asc(entityRoot.get(sortField)));


        // запрос
        entityManager.clear(); // плохо. Спросить, как реализовать иначе.
                               // Без этого - выдача Hibernate списка сущностей без изменений в БД

        TypedQuery<Entity> typedQuery = entityManager.createQuery(query);

        pageNumber = pageNumber == null ? DEFAULT_PAGE_NUMBER : pageNumber;
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;

        typedQuery.setFirstResult(pageNumber * pageSize);
        typedQuery.setMaxResults(pageSize);


        return typedQuery.getResultList();
    }

    private Predicate getPredicate(EntitySearchCriteria entitySearchCriteria, Root<Entity> entityRoot) {
        String name = entitySearchCriteria.getName();
        String title = entitySearchCriteria.getTitle();
        Race race = entitySearchCriteria.getRace();
        Profession profession = entitySearchCriteria.getProfession();
        Long before = entitySearchCriteria.getBefore();
        Long after = entitySearchCriteria.getAfter();
        Boolean banned = entitySearchCriteria.getBanned();
        Integer minExperience = entitySearchCriteria.getMinExperience();
        Integer maxExperience = entitySearchCriteria.getMaxExperience();
        Integer minLevel = entitySearchCriteria.getMinLevel();
        Integer maxLevel = entitySearchCriteria.getMaxLevel();


        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(name)) {
            predicates.add(
                    criteriaBuilder.like(entityRoot.get("name"), "%"+ name +"%")
            );
        }

        if (Objects.nonNull(title)) {
            predicates.add(
                    criteriaBuilder.like(entityRoot.get("title"), "%"+ title +"%")
            );
        }


        if (Objects.nonNull(race)) {
            predicates.add(
                    criteriaBuilder.equal(entityRoot.get("race"), race)
            );
        }

        if (Objects.nonNull(profession)) {
            predicates.add(
                    criteriaBuilder.equal(entityRoot.get("profession"), profession)
            );
        }

        if (Objects.nonNull(after)) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(entityRoot.get("birthday"), new Date(after))
            );
        }

        if (Objects.nonNull(before)) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(entityRoot.get("birthday"), new Date(before))
            );
        }

        if (Objects.nonNull(banned)) {
            predicates.add(
                    criteriaBuilder.equal(entityRoot.get("banned"), banned)
            );
        }

        if (Objects.nonNull(minExperience)) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(entityRoot.get("experience"), minExperience)
            );
        }

        if (Objects.nonNull(maxExperience)) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(entityRoot.get("experience"), maxExperience)
            );
        }

        if (Objects.nonNull(minLevel)) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(entityRoot.get("level"), minLevel)
            );
        }

        if (Objects.nonNull(maxLevel)) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(entityRoot.get("level"), maxLevel)
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}