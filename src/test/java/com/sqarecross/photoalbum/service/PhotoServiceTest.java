package com.sqarecross.photoalbum.service;

import com.sqarecross.photoalbum.domain.Album;
import com.sqarecross.photoalbum.domain.Photo;
import com.sqarecross.photoalbum.dto.PhotoDto;
import com.sqarecross.photoalbum.repository.AlbumRepository;
import com.sqarecross.photoalbum.repository.PhotoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
class PhotoServiceTest {
    @Autowired
    PhotoService photoService;
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    AlbumRepository albumRepository;

    @Test
    void getPhotoTest() {
        Album album = new Album();
        Album savedAlbum = this.albumRepository.save(album);
        Photo photo = new Photo();

        photo.setFileName("test");
        Photo savedPhoto = this.photoRepository.save(photo);

        PhotoDto savedPhotoDto = this.photoService.getPhoto(savedAlbum.getAlbumId(), savedPhoto.getPhotoId());
        assertEquals("test", savedPhotoDto.getFileName());
    }
}