package com.tfg.apirest.repository;

import com.tfg.apirest.entity.Farmaco;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@AllArgsConstructor
public class CustomRepositoryImpl<T> implements CustomRepository<T> {
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void refresh(T t) {
        entityManager.refresh(t);
    }
}