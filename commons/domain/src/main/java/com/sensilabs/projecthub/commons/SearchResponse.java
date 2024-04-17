package com.sensilabs.projecthub.commons;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchResponse<T> {
    private List<T> items;
    private Long total;
}
