package com.example.software.Repositories;

import org.springframework.data.jpa.domain.Specification;

import com.example.software.Models.Aplication;
import com.example.software.Models.Server;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ServerSpecs {

    public static Specification<Server> serversWithMultipleApplications() {
        return new Specification<Server>() {
            @Override
            public Predicate toPredicate(Root<Server> root,
                    CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join<Server, Aplication> join = root.join("aplications", JoinType.INNER);
                Expression<Long> countExpression = criteriaBuilder.count(join);
                Expression<Long> oneExpression = criteriaBuilder.literal(1l);

                Predicate greterThanOne = criteriaBuilder.greaterThan(
                        countExpression,
                        oneExpression);
                query.groupBy(root.get("id"));
                query.having(greterThanOne);
                return null;
            }
        };
    }
}
