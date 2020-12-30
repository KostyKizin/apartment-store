package com.demo.web;

import com.demo.entity.User;
import com.demo.entity.dto.ApartmentDto;
import com.demo.entity.dto.ApartmentRequest;
import com.demo.service.ApartmentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@AllArgsConstructor
public class ApartmentController {
    private final ApartmentService service;


    @GetMapping("/apartment/all")
    public ApartmentPageResponse getAll(
            @SessionAttribute(name = "user") User requestAuthor,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "price", required = false) Integer price,
            @RequestParam(value = "direction", required = false) Sort.Direction direction,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        final ApartmentRequest request = ApartmentRequest.builder()
                .country(country)
                .direction(direction)
                .price(price)
                .page(page)
                .size(size)
                .requestAuthor(requestAuthor)
                .build();

        return service.findAll(request);
    }

    @PostMapping("/apartment")
    public ApartmentDto create(@SessionAttribute(name = "user") User requestAuthor,
                               @RequestBody ApartmentDto dto) {
        return new ApartmentDto(service.create(dto, requestAuthor));
    }

    @DeleteMapping("/apartment/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        service.delete(id);
        return HttpStatus.OK;
    }


}
