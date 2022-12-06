package com.solution.survey.service;

import com.solution.survey.model.entity.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


public abstract class BaseService<T extends BaseEntity>{

    private static final Logger log =
            LoggerFactory.getLogger(BaseService.class);

    protected abstract JpaRepository<T, Long> getRepository();

    public Optional<T> findById(Long id) {
        log.debug("Looking for an entity with id {}", id);
        return getRepository().findById(id);
    }

    public List<T> findAllByIds(List<Long> ids) {
        log.debug("Looking for entities with ids {}", ids);
        return getRepository().findAllById(ids);
    }

    public T save(@Valid T entity) {
        log.debug("Saving an entity {}", entity);
        return getRepository().save(entity);
    }

    public List<T> saveAll(List<T> entities) {
        log.debug("Saving entities {}", entities);
        return getRepository().saveAll(entities);
    }
}
