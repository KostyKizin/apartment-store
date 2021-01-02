package com.demo.web;

public enum ApartmentOrderBy {
    COUNTRY("country.name"),
    PRICE("price");

    private String fieldName;

    ApartmentOrderBy(String field) {
        this.fieldName = field;
    }

    public String getFieldName() {
        return fieldName;
    }

}
