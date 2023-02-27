package com.sqarecross.photoalbum.service;

import com.sqarecross.photoalbum.domain.Album;
import com.sqarecross.photoalbum.repository.AlbumRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AlbumServiceTest {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    AlbumService albumService;

    @Test
    void getAlbum() {
        Album album = new Album();
        album.setAlbumName("테스트");
        Album savedAlbum = albumRepository.save(album);

        Album resAlbum = albumService.getAlbum(savedAlbum.getAlbumId());
        assertEquals("테스트", resAlbum.getAlbumName());
    }

    @Test
    void findAlbum(){
        Album album = new Album();
        album.setAlbumName("테스트");
        Album savedAlbum = albumRepository.save(album);

        Album resAlbum = albumService.getAlbum(savedAlbum.getAlbumName());
        assertEquals("테스트", resAlbum.getAlbumName());
    }

    @Test
    void when_NotFoundAlbum_then_ThrowEntityNotFoundException(){
        Album album = new Album();
        album.setAlbumName("테스트");
        Album savedAlbum = albumRepository.save(album);

        assertThatThrownBy(() -> albumService.getAlbum(3L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("앨범 아이디 3로 조회되지 않았습니다.");
    }
}