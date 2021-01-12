package com.demo.repository;

import com.demo.entity.Apartment;
import com.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ApartmentRepository extends PagingAndSortingRepository<Apartment, Long> {

    Optional<Apartment> findByIdAndOwner(Long id, User owner);

    @Query("select distinct a from Apartment a left join Deal d on d.apartment = a where d is null or d.status in ('IN_PROGRESS', 'CANCELED')")
    Page<Apartment> findAllForResponse(Pageable pageable);
}
