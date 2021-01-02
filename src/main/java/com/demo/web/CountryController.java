package com.demo.web;

import com.demo.entity.dto.CountryDto;
import com.demo.service.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class CountryController {
    private final CountryService countryService;

    @GetMapping("/countries")
    public Collection<CountryDto> getAll() {
        return countryService.findAll().stream()
                .map(CountryDto::new)
                .collect(Collectors.toList());
    }


}
