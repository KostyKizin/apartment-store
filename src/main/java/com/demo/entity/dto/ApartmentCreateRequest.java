package com.demo.entity.dto;

import lombok.Data;

@Data
public class ApartmentCreateRequest {
    private Long id;
    private int numberOfRooms;
    private String address;
    private int price;
    private Long countryId;
}
