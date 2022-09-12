package com.tfg.apirest.repository;

import com.tfg.apirest.entity.Farmaco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface CustomRepository<T> {
    void refresh(T t);
}