package com.demo.entity.dto;

import com.demo.entity.Country;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
public class CountryDto {
    private Long id;
    private String name;
    public CountryDto(Country country) {
        this.id = country.getId();
        this.name = country.getName();
    }
}
