package com.spring_jpa_cache.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serializable;

@Entity
@Data
@Table(name = "departments")
@SQLRestriction("deleted = false")
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Embedded
    private Address address;

    private String description;

    private boolean deleted;
}