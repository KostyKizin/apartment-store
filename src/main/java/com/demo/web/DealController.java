package com.demo.web;

import com.demo.entity.Apartment;
import com.demo.entity.Deal;
import com.demo.entity.Status;
import com.demo.entity.User;
import com.demo.entity.dto.DealViewDto;
import com.demo.service.ApartmentService;
import com.demo.service.DealPageResponse;
import com.demo.service.DealService;
import com.demo.service.DealViewRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@AllArgsConstructor
public class DealController {

    private final ApartmentService apartmentService;
    private final DealService dealService;

    @PostMapping("/deal/{apartmentId}")
    public DealViewDto create(@SessionAttribute(name = "user") User requestAuthor,
                              @PathVariable Long apartmentId) {
        Apartment apartment = apartmentService.getById(apartmentId);
        Deal deal = dealService.create(apartment, requestAuthor);
        return new DealViewDto(deal);

    }

    @PutMapping("/deal/status/{id}/{status}")
    public HttpStatus cancel(@SessionAttribute(name = "user") User requestAuthor,
                             @PathVariable Status status,
                             @PathVariable Long id) {
        dealService.updateStatus(id, requestAuthor, status);
        return HttpStatus.OK;
    }


    @GetMapping("/deals")
    public DealPageResponse findAllByStatus(@SessionAttribute(name = "user") User requestAuthor,
                                            @RequestParam(name = "status", required = false, defaultValue = "IN_PROGRESS") Status status,
                                            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                            @RequestParam(name = "direction", required = false, defaultValue = "asc") Sort.Direction direction,
                                            @RequestParam(name = "field", required = false, defaultValue = "date") String field) {

        final DealViewRequest request = DealViewRequest.builder()
                .user(requestAuthor)
                .direction(direction)
                .page(page)
                .size(size)
                .status(status)
                .sortingProperty(field)
                .build();

        return dealService.getPage(request);
    }

}
