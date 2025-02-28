package com.spring_jpa_cache.model;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Data
@SuperBuilder

@Document(collection = "photos")
public class UserPhoto {
    @Id
    private String id;
    private String username;
    private Binary photoData;
}