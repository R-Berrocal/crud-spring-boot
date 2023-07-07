package com.example.software.Repositories;

import org.springframework.data.jpa.domain.Specification;

import com.example.software.Models.Aplication;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class AplicationSpecs {
    public static Specification<Aplication> aplicationByServerOs(String os) {
        return new Specification<Aplication>() {
            @Override
            public Predicate toPredicate(Root<Aplication> root,
                    CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
               return criteriaBuilder.equal(root.get("server").get("os"), os);
            }
        };
    }

    public static Specification<Aplication> aplicationByName(String name) {
        return new Specification<Aplication>() {
            @Override
            public Predicate toPredicate(Root<Aplication> root,
                    CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
               return criteriaBuilder.equal(root.get("name"), name);
            }
        };
    }
}
