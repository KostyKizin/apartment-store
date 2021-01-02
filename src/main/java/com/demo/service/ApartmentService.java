package com.demo.service;

import com.demo.entity.Apartment;
import com.demo.entity.Country;
import com.demo.entity.Status;
import com.demo.entity.User;
import com.demo.entity.dto.ApartmentCreateRequest;
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

    public Apartment create(ApartmentCreateRequest request, User requestAuthor) {
        if (request.getId() != null) {
            Apartment apartment = repository.findByIdAndOwner(request.getId(), requestAuthor)
                    .orElseThrow(() -> new ServiceException(String.format("Apartment did not found id %s", request.getId())));
            this.update(apartment, request);
            return repository.save(apartment);
        } else {
            Apartment apartment = this.map(request, requestAuthor);
            return repository.save(apartment);
        }
    }


    public void delete(Long id, User requestAuthor) {
        Apartment apartment = repository.findByIdAndOwner(id, requestAuthor).orElse(null);
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
        return dealRepository.findAllByApartment(apartment)
                .stream()
                .anyMatch(deal -> activeStatuses.contains(deal.getStatus()));
    }

    private Apartment map(ApartmentCreateRequest request, User user) {
        Apartment apartment = new Apartment();
        apartment.setOwner(user);
        this.update(apartment, request);
        return apartment;
    }


    private void update(Apartment apartment, ApartmentCreateRequest request) {

        if (request.getAddress() != null) {
            apartment.setAddress(request.getAddress());
        }

        if (request.getCountryId() != null) {
            Country country = this.getCountry(request.getCountryId());
            apartment.setCountry(country);
        }

        if (request.getPrice() > 0) {
            request.setPrice(request.getPrice());
        }

        if (request.getNumberOfRooms() > 0) {
            apartment.setNumberOfRooms(request.getNumberOfRooms());
        }
    }

    private Country getCountry(Long countryId) {
        return countryRepository.findById(countryId).orElseThrow(() -> new ServiceException(String.format("Country not found, %s", countryId)));
    }
}
