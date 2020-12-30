package com.demo.web;

import com.demo.entity.dto.CountryDto;
import com.demo.service.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @PostMapping("/country")
    public CountryDto create(@RequestBody CountryDto dto) {
        return new CountryDto(countryService.create(dto));
    }

    @GetMapping("/countries")
    public Collection<CountryDto> getAll() {
        return countryService.findAll().stream()
                .map(CountryDto::new)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/country/{id}")
    public HttpStatus remove(@PathVariable Long id) {
        countryService.remove(id);
        return HttpStatus.OK;
    }

}
