package com.demo.service;

import com.demo.entity.Status;
import com.demo.entity.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@Builder
public class DealViewRequest {
    private User user;
    private int page;
    private int size;
    private Sort.Direction direction;
    private String sortingProperty;
    private Status status;

    public Pageable createPageable() {
        if (isSorted()) {
            return PageRequest.of(page, size, direction, getSortingProperty());
        }
        return PageRequest.of(page, size);
    }

    private String getSortingProperty() {

        if ("COUNTRY".equals(sortingProperty)) {
            return "apartment.country";
        } else if ("DATE".equals(sortingProperty)) {
            return "date";
        }
        return null;
    }

    private boolean isSorted() {
        return direction != null && getSortingProperty() != null;
    }

}
