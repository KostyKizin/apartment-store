package com.demo.repository;

import com.demo.entity.Apartment;
import com.demo.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

public interface ApartmentRepository extends PagingAndSortingRepository<Apartment, Long> {

    boolean existsByAddressAndCountry(String address, Country country);

    Collection<Apartment> findAllByCountry(Country country);

    @Query(value = "select * from apartment" +
                   " left join deal d on apartment.id = d.apartment_id" +
            "        where d.id is null ", nativeQuery = true)
    Page<Apartment> findAllByDealsIsNull(Pageable pageable);
}
