package com.demo.web;

import com.demo.entity.Apartment;
import com.demo.entity.dto.ApartmentDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ApartmentPageResponse {
    private Collection<ApartmentDto> resultList;
    private long totalElements;
    private long totalPages;

    public ApartmentPageResponse(Page<Apartment> page) {
        if (page.hasContent()) {
            this.resultList = page.stream()
                    .map(ApartmentDto::new)
                    .collect(Collectors.toList());
        } else {
            this.resultList = Collections.emptyList();
        }

        this.totalElements = page.getTotalElements();
        totalPages = page.getTotalPages();

    }
}
