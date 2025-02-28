package com.spring_jpa_cache.repository;

import com.spring_jpa_cache.model.UserPhoto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPhotoRepository extends MongoRepository<UserPhoto, String> {
    Optional<UserPhoto> findByUsername(String username);
}