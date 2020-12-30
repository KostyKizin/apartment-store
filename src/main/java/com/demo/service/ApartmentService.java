package com.demo.service;

import com.demo.entity.Apartment;
import com.demo.entity.Country;
import com.demo.entity.Status;
import com.demo.entity.User;
import com.demo.entity.dto.ApartmentDto;
import com.demo.entity.dto.ApartmentRequest;
import com.demo.repository.ApartmentRepository;
import com.demo.repository.CountryRepository;
import com.demo.repository.DealRepository;
import com.demo.service.exception.ServiceException;
import com.demo.web.ApartmentPageResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Set;

@AllArgsConstructor
@Service
public class ApartmentService {
    private final ApartmentRepository repository;
    private final CountryRepository countryRepository;
    private final DealRepository dealRepository;

    public ApartmentPageResponse findAll(ApartmentRequest request) {
        Page<Apartment> page = repository.findAllByDealsIsNull(request.getPageRequest());
        return new ApartmentPageResponse(page);
    }

    public Apartment create(ApartmentDto dto, User requestAuthor) {
        if (dto.getId() != null) {
            if (repository.existsByAddressAndCountry(dto.getAddress(), getCountry(dto.getCountryName()))) {
                throw new ServiceException(String.format("Apartment by address %s and country %s already exists", dto.getAddress(), dto.getCountryName()));
            }
        }
        return repository.save(this.map(dto, requestAuthor));
    }


    public void delete(Long id) {
        Apartment apartment = repository.findById(id).orElse(null);
        if (apartment != null) {
            if (hasDeals(apartment)) {
                throw new ServiceException("Can't delete apartment! There are deals using the apartment!");
            } else {
                repository.delete(apartment);
            }
        }

    }


    public Apartment getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ServiceException(String.format("Apartment by id %s is not exists", id)));
    }

    private boolean hasDeals(Apartment apartment) {
        return existsActiveDeals(apartment);
    }

    private boolean existsActiveDeals(Apartment apartment) {
        Set<Status> activeStatuses = Set.of(Status.DONE, Status.IN_PROGRESS);
        return dealRepository.findAllByApartment(apartment).stream().anyMatch(deal -> activeStatuses.contains(deal.getStatus()));
    }

    private Country getCountry(String name) {
        return countryRepository.findByName(name)
                .orElseThrow(() -> new ServiceException(String.format("Can't find country by name %s", name)));
    }


    private Apartment map(ApartmentDto dto, User user) {
        return Apartment.builder()
                .address(dto.getAddress())
                .numberOfRooms(dto.getNumberOfRooms())
                .price(dto.getPrice())
                .owner(user)
                .id(dto.getId())
                .country(getCountry(dto.getCountryName()))
                .build();
    }
}
