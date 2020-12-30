package com.demo.service;

import com.demo.entity.Apartment;
import com.demo.entity.Country;
import com.demo.entity.Status;
import com.demo.entity.dto.CountryDto;
import com.demo.repository.ApartmentRepository;
import com.demo.repository.CountryRepository;
import com.demo.repository.DealRepository;
import com.demo.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class CountryService {
    private final CountryRepository repository;
    private final ApartmentRepository apartmentRepository;
    private final DealRepository dealRepository;

    public Country create(CountryDto dto) {
        repository.findByName(dto.getName()).ifPresent(country -> {
            throw new ServiceException(String.format("country by name %s already exists", country.getName()));
        });
        return repository.save(map(dto));
    }

    public void remove(Long id) {
        Country country = repository.findById(id).orElse(null);
        if (country != null) {
            Collection<Apartment> apartments = apartmentRepository.findAllByCountry(country);
            if (hasDeals(apartments)) {
                throw new ServiceException("Can't delete country! There are deals created by apartments from the country");
            } else {
                repository.delete(country);
            }
        }

    }

    private boolean hasDeals(Collection<Apartment> apartments) {
        return apartments.stream().anyMatch(this::existsActiveDeals);
    }

    private boolean existsActiveDeals(Apartment apartment) {
        Set<Status> activeStatuses = Set.of(Status.DONE, Status.IN_PROGRESS);
        return dealRepository.findAllByApartment(apartment).stream().anyMatch(deal -> activeStatuses.contains(deal.getStatus()));
    }

    public Collection<Country> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
    }


    private Country map(CountryDto dto) {
        return new Country(dto.getId(), dto.getName());
    }
}
