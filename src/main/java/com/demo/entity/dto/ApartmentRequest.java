package com.demo.entity.dto;

import com.demo.entity.User;
import com.demo.web.ApartmentOrderBy;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Builder
@Data
public class ApartmentRequest {
    private User requestAuthor;
    private int page;
    private int size;
    private ApartmentOrderBy orderBy;
    private Sort.Direction direction;


    @JsonIgnore
    public PageRequest getPageRequest() {
        if (isSorted()) {
            return PageRequest.of(page, size, direction, getProperty());
        } else {
            return PageRequest.of(page, size);
        }
    }

    @JsonIgnore
    private boolean isSorted() {
        return direction != null && getProperty() != null;
    }


    @JsonIgnore
    private String getProperty() {
        if (orderBy != null) {
            return orderBy.getFieldName();
        }
        return null;
    }


}
