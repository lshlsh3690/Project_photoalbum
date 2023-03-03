package com.sqarecross.photoalbum.controller;

import com.sqarecross.photoalbum.dto.PhotoDto;
import com.sqarecross.photoalbum.service.PhotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/albums/{albumId}/photos")
public class PhotoController {
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/{photoId}")
    public ResponseEntity<PhotoDto> getPhotoInfo(@PathVariable("albumId") final Long albumId,
                                                 @PathVariable("photoId") final Long photoId) {
        PhotoDto photoDto = this.photoService.getPhoto(albumId, photoId);
        return ResponseEntity.ok(photoDto);
    }

    @PostMapping
    public ResponseEntity<List<PhotoDto>>uploadPhotos(@PathVariable("albumId")final Long albumId,
                                                      @RequestParam("photos")MultipartFile[] files) {
        List<PhotoDto>photos = new ArrayList<>();
        for(MultipartFile file : files){
            PhotoDto photoDto = this.photoService.savePhoto(file,albumId);
            photos.add(photoDto);
        }
        return ResponseEntity.ok(photos);
    }
}
