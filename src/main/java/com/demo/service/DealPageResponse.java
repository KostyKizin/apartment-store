package com.demo.service;

import com.demo.entity.Deal;
import com.demo.entity.dto.DealViewDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class DealPageResponse {
    private List<DealViewDto> result;
    private long totalElement;
    private long totalPages;

    public DealPageResponse(Page<Deal> page) {
        if (page.hasContent()) {
            this.result = page.getContent().stream()
                    .map(DealViewDto::new)
                    .collect(Collectors.toList());
        } else {
            this.result = Collections.emptyList();
        }
        this.totalElement = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }

}
