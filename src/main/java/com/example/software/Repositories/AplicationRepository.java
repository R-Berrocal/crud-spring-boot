package com.example.software.Repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.example.software.Models.Aplication;

public interface AplicationRepository extends CrudRepository<Aplication, Integer>, JpaSpecificationExecutor<Aplication> {
    
}
