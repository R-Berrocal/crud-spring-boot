package com.example.software.Repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.example.software.Models.Server;

public interface ServerRepository extends CrudRepository<Server, Integer>, JpaSpecificationExecutor<Server>  {

}
