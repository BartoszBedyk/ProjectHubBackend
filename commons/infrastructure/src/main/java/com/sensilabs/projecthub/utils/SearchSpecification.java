package com.sensilabs.projecthub.utils;

import com.sensilabs.projecthub.commons.*;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class SearchSpecification {

    public static <T> Specification<T> buildSpecification(List<SearchFormCriteria> criteriaList) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (SearchFormCriteria criteria : criteriaList) {
                String[] paramField = criteria.getFieldName().split("\\.");
                Path<?> fieldPath = getPath(root, paramField);
                predicates.add(buildPredicateForCriteria(cb, fieldPath, criteria));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    static <T> Path<?> getPath(Root<T> root, String[] paramField) {
        if (paramField.length == 2 && paramField[0].equals("params")) {
            return root.joinList("params", JoinType.LEFT).get(paramField[1]);
        } else {
            return root.get(paramField[0]);
        }
    }

    static Predicate buildPredicateForCriteria(CriteriaBuilder cb, Path<?> fieldPath, SearchFormCriteria criteria) {
        switch (criteria.getOperator()) {
            case EQUALS:
                return cb.equal(fieldPath, convertToType(criteria.getValue(), fieldPath.getJavaType()));
            case NOT_EQUALS:
                return cb.notEqual(fieldPath, convertToType(criteria.getValue(), fieldPath.getJavaType()));
            case LIKE:
                return cb.like(fieldPath.as(String.class), "%" + criteria.getValue() + "%");
            case GR:
                return cb.greaterThan(fieldPath.as(Comparable.class), (Comparable) convertToType(criteria.getValue(), fieldPath.getJavaType()));
            case GRE:
                return cb.greaterThanOrEqualTo(fieldPath.as(Comparable.class), (Comparable) convertToType(criteria.getValue(), fieldPath.getJavaType()));
            case LS:
                return cb.lessThan(fieldPath.as(Comparable.class), (Comparable) convertToType(criteria.getValue(), fieldPath.getJavaType()));
            case LSE:
                return cb.lessThanOrEqualTo(fieldPath.as(Comparable.class), (Comparable) convertToType(criteria.getValue(), fieldPath.getJavaType()));
            default:
                throw new ApplicationException(ErrorCode.UNSUPPORTED_OPERATOR, "Unsupported operator: " + criteria.getOperator());
        }
    }

    static Object convertToType(Object value, Class<?> targetType) {
        if (targetType.isEnum()) {
            return Enum.valueOf((Class<Enum>) targetType, value.toString());
        } else if (targetType.equals(Instant.class)) {
            return Instant.parse(value.toString());
        } else {
            return value;
        }
    }

    public static PageRequest getPageRequest(SearchForm searchForm) {
        int page = searchForm.getPage() != null ? searchForm.getPage() - 1 : 0;
        int size = searchForm.getSize() != null ? searchForm.getSize() : Integer.MAX_VALUE;
        Sort sort = getSort(searchForm.getSort());
        return PageRequest.of(page, size, sort);
    }

    static Sort getSort(SearchSort searchSort) {
        if (searchSort == null) {
            return Sort.unsorted();
        }
        return searchSort.getOrder() == SearchSortOrder.ASC ? Sort.by(searchSort.getBy()).ascending() : Sort.by(searchSort.getBy()).descending();
    }
}
