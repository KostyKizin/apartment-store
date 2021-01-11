package com.demo.entity.dto;

import com.demo.entity.Deal;
import com.demo.entity.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class DealViewDto {
    private Long id;
    private ApartmentDto apartmentDto;
    private UserDto seller;
    private UserDto buyer;
    private Status status;
    private LocalDate date;

    public DealViewDto(Deal deal) {
        this.apartmentDto = new ApartmentDto(deal.getApartment());
        this.seller = new UserDto(deal.getApartment().getOwner());
        this.buyer = new UserDto(deal.getBuyer());
        this.status = deal.getStatus();
        this.date = deal.getDate();
        this.id = deal.getId();
    }
}
