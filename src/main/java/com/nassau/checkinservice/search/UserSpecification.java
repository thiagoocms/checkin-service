package com.nassau.checkinservice.search;

import com.nassau.checkinservice.domain.User;
import com.nassau.checkinservice.util.FilterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class UserSpecification implements Specification<User> {

    private final transient List<SearchCriteria> criterias;

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    public static UserSpecification build(List<SearchCriteria> criterias) {
        return new UserSpecification(criterias);
    }

    //**********************************************************************************
    // PUBLIC STATIC FUNCTIONS
    //**********************************************************************************

    public static Specification<User> listAllByCriteria(List<SearchCriteria> criterias) {

        return (root, query, builder) -> {
            List<Predicate> predicates = addPredicates(criterias, root, builder);
            buildOrderBy(query, criterias, root, builder);
            return FilterUtil.andTogether(predicates, builder);
        };
    }

    //**********************************************************************************
    // PRIVATE FUNCTIONS
    //**********************************************************************************

    private static List<String> getOrderByFields(List<SearchCriteria> criterias) {
        for (SearchCriteria criteria : criterias) {
            if (criteria.getKey().equals(SearchCriteria.ORDER_BY_FIELDS_KEY) && criteria.getOrderByFields().size() > 0)
                return criteria.getOrderByFields();
        }
        return null;
    }

    private static void buildOrderBy(CriteriaQuery<?> query, List<SearchCriteria> criterias,
                                     Root<User> root, CriteriaBuilder builder) {

        String order = getOrder(criterias);
        List<String> orderByOptions = cleanOptions(getOrderByFields(criterias));
        List<Order> orders = new ArrayList<>();
        for (String field : orderByOptions) {
            orders.add(buildOrder(root, builder, order, field));
        }
        if (orders.size() > 0) {
            query.orderBy(orders);
        }
    }

    private static List<String> cleanOptions(List<String> orderByOptions) {
        List<String> options = new ArrayList<>();
        if (Objects.nonNull(orderByOptions)) {
            for (String string : orderByOptions) {
                String option = string.replace("[", "").replace("]", "");
                if (Objects.nonNull(option) && !option.isEmpty()) {
                    options.add(option);
                }
            }
        }
        return options;
    }

    private static Order buildOrder(Root<User> root, CriteriaBuilder builder, String order, String field) {
        switch (order) {
            case SearchCriteria.ORDER_ASC:
                return builder.asc(buildExpression(root, field));
            case SearchCriteria.ORDER_DESC:
                return builder.desc(buildExpression(root, field));
        }
        return builder.asc(buildExpression(root, field));
    }

    private static Expression<?> buildExpression(Root<User> root, String field) {
        return root.<String>get(field);
    }

    private static String getOrder(List<SearchCriteria> criterias) {
        for (SearchCriteria criteria : criterias) {
            if (criteria.getOrder() != null)
                return criteria.getOrder();
        }
        return SearchCriteria.ORDER_ASC;
    }

    private static List<Predicate> addPredicates(List<SearchCriteria> criterias, Root<User> root, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.<Boolean>get("deleted"), Boolean.FALSE));
        for (SearchCriteria criteria : criterias) {
            addPredicate(root, builder, predicates, criteria);
        }
        return predicates;
    }

    private static void addPredicate(Root<User> root, CriteriaBuilder builder, List<Predicate> predicates, SearchCriteria criteria) {
        String field = criteria.getKey();
        switch (field) {
            case "login":
            case "documentNumber":
                predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
                break;
        }
    }

}
