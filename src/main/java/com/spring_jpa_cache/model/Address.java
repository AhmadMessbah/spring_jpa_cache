package com.spring_jpa_cache.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Address {
    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;
}