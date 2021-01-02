package com.demo.entity.dto;

import com.demo.entity.Apartment;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApartmentDto {

    private Long id;
    private int numberOfRooms;
    private String address;
    private int price;
    private String countryName;
    private UserDto owner;


    public ApartmentDto(Apartment apartment) {
        this.id = apartment.getId();
        this.numberOfRooms = apartment.getNumberOfRooms();
        this.address = apartment.getAddress();
        this.price = apartment.getPrice();
        this.countryName = apartment.getCountry().getName();
        this.owner = new UserDto(apartment.getOwner());
    }
}
