package com.game.repository;

import com.game.entity.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface EntityRepository extends JpaRepository<Entity, Long>, JpaSpecificationExecutor<Entity> {
    long count();
    void deleteById(Long aLong);
}