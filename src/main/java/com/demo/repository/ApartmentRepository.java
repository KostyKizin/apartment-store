package com.demo.repository;

import com.demo.entity.Apartment;
import com.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ApartmentRepository extends PagingAndSortingRepository<Apartment, Long> {

    Optional<Apartment> findByIdAndOwner(Long id, User owner);

    Page<Apartment> findAllByDealsIsNull(Pageable pageable);
}
