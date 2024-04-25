package com.sensilabs.projecthub.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchFormCriteria {
    private String fieldName;
    private Object value;
    private CriteriaOperator operator;
}
