package com.sqarecross.photoalbum.service;

import com.sqarecross.photoalbum.domain.Album;
import com.sqarecross.photoalbum.domain.Photo;
import com.sqarecross.photoalbum.dto.AlbumDto;
import com.sqarecross.photoalbum.repository.AlbumRepository;
import com.sqarecross.photoalbum.repository.PhotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class AlbumServiceTest {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    AlbumService albumService;

    @Test
    void getAlbum() {
        Album album = new Album();
        album.setAlbumName("테스트");
        Album savedAlbumDto = albumRepository.save(album);

        AlbumDto resAlbumDto = albumService.getAlbum(savedAlbumDto.getAlbumId());
        assertEquals("테스트", resAlbumDto.getAlbumName());
    }

    @Test
    void findByAlbumName(){
        Album album = new Album();
        album.setAlbumName("테스트");
        Album savedAlbum = albumRepository.save(album);

        AlbumDto resAlbum = albumService.getAlbum(savedAlbum.getAlbumName());
        assertEquals("테스트", resAlbum.getAlbumName());
    }

    @Test
    void when_NotFoundAlbum_then_ThrowEntityNotFoundException(){
        Album album = new Album();
        album.setAlbumName("테스트");
        Album savedAlbumDto = albumRepository.save(album);

        assertThatThrownBy(() -> albumService.getAlbum(3L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("앨범 아이디 3로 조회되지 않았습니다.");
    }

    @Test
    void testPhotoCount(){
        Album album = new Album();
        album.setAlbumName("테스트");
        Album savedAlbum = albumRepository.save(album);

        //사진을 생성하고, setAlbum을 통해 앨범을 지정해준 이후, repository에 사진을 저장한다
        Photo photo1 = new Photo();
        photo1.setFileName("사진1");
        photo1.setAlbum(savedAlbum);
        photoRepository.save(photo1);

        Photo photo2 = new Photo();
        photo2.setFileName("사진2");
        photo2.setAlbum(savedAlbum);
        photoRepository.save(photo2);

        assertEquals(2, photoRepository.countByAlbum_AlbumId(savedAlbum.getAlbumId()));
    }
}