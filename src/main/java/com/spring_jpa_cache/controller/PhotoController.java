package com.spring_jpa_cache.controller;

import com.spring_jpa_cache.model.UserPhoto;
import com.spring_jpa_cache.repository.UserPhotoRepository;
import com.spring_jpa_cache.repository.UserRepository;
import org.bson.types.Binary;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
public class PhotoController {

    private final UserRepository userRepository;
    private final UserPhotoRepository userPhotoRepository;

    public PhotoController(UserRepository userRepository, UserPhotoRepository userPhotoRepository) {
        this.userRepository = userRepository;
        this.userPhotoRepository = userPhotoRepository;
    }

    @GetMapping("/upload-photo")
    public String showUploadForm(Model model, Authentication authentication) {
        model.addAttribute("message", "");
        return "upload-photo";
    }

    @PostMapping("/upload-photo")
    public String uploadPhoto(@RequestParam("photo") MultipartFile file, Authentication authentication, Model model) throws IOException {
        String username = authentication.getName();
        UserPhoto userPhoto = userPhotoRepository.findByUsername(username)
                .orElse(new UserPhoto());
        userPhoto.setUsername(username);
        userPhoto.setPhotoData(new Binary(file.getBytes()));
        userPhotoRepository.save(userPhoto);
        model.addAttribute("message", "Photo uploaded successfully!");
        return "upload-photo";
    }

    @GetMapping("/photo/{username}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String username) {
        Optional<UserPhoto> photo = userPhotoRepository.findByUsername(username);
        if (photo.isPresent() && photo.get().getPhotoData() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(photo.get().getPhotoData().getData());
        }
        return ResponseEntity.notFound().build();
    }
}