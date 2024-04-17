package com.sensilabs.projecthub.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchForm {
    private List<SearchFormCriteria> criteria;
    private Integer page;
    private Integer size;
    private SearchSort sort;
}
