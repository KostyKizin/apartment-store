package com.demo.service;

import com.demo.entity.Apartment;
import com.demo.entity.Deal;
import com.demo.entity.Status;
import com.demo.entity.User;
import com.demo.repository.DealRepository;
import com.demo.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class DealService {
    private final DealRepository dealRepository;

    public Deal create(Apartment apartment, User requestAuthor) {
        if (!requestAuthor.isBuyer()) {
            throw new ServiceException("Request author is not buyer");
        }

        if (this.existsActiveDeals(apartment)) {
            throw new ServiceException("The apartment is part of deal");
        }

        return dealRepository.save(Deal.builder()
                .apartment(apartment)
                .buyer(requestAuthor)
                .date(LocalDate.now())
                .status(Status.IN_PROGRESS)
                .build());

    }

    public void cancel(Long id, User user) {
        Deal deal = this.getDeal(id, user)
                .orElseThrow(() -> new ServiceException(String.format("Can't find deal by id %s and user role  %s", id, user.getRole())));
        deal.setStatus(Status.CANCELED);
        dealRepository.save(deal);
    }


    private Optional<Deal> getDeal(Long id, User user) {
        if (user.isBuyer()) {
            return dealRepository.findByIdAndBuyer(id, user);
        }

        return dealRepository.findByIdAndApartmentOwner(id, user);
    }

    public DealPageResponse getPage(DealViewRequest request) {
        Page<Deal> page = this.getAll(request);
        return new DealPageResponse(page);
    }


    private Page<Deal> getAll(DealViewRequest request) {
        if (request.getUser().isBuyer()) {
            return dealRepository.findAllByBuyerAndStatus(request.getUser(), request.getStatus(), request.createPageable());
        }
        return dealRepository.findAllByApartmentOwnerAndStatus(request.getUser(), request.getStatus(), request.createPageable());
    }

    private boolean existsActiveDeals(Apartment apartment) {
        Set<Status> activeStatuses = Set.of(Status.DONE, Status.IN_PROGRESS);
        return dealRepository.findAllByApartment(apartment).stream().anyMatch(deal -> activeStatuses.contains(deal.getStatus()));
    }


}
