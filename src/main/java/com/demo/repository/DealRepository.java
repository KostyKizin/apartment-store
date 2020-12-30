package com.demo.repository;

import com.demo.entity.Apartment;
import com.demo.entity.Deal;
import com.demo.entity.Status;
import com.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.Optional;

public interface DealRepository extends PagingAndSortingRepository<Deal, Long> {

    Collection<Deal> findAllByApartment(Apartment apartment);

    Page<Deal> findAllByBuyerAndStatus(User buyer, Status status, Pageable pageable);

    Page<Deal> findAllByApartmentOwnerAndStatus(User owner, Status status, Pageable pageable);

    Optional<Deal> findByIdAndBuyer(Long id, User buyer);

    Optional<Deal> findByIdAndApartmentOwner(Long id, User owner);
}
