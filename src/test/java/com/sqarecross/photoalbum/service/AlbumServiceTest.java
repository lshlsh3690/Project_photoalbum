package com.sqarecross.photoalbum.service;

import com.sqarecross.photoalbum.Constants;
import com.sqarecross.photoalbum.domain.Album;
import com.sqarecross.photoalbum.domain.Photo;
import com.sqarecross.photoalbum.dto.AlbumDto;
import com.sqarecross.photoalbum.mapper.AlbumMapper;
import com.sqarecross.photoalbum.repository.AlbumRepository;
import com.sqarecross.photoalbum.repository.PhotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

        assertThatThrownBy(() -> albumService.getAlbum(5000L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("앨범 아이디 5000로 조회되지 않았습니다.");
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

    @Test
    void createAlbumTest() throws IOException {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setAlbumName("test");
        AlbumDto savedAlbumDto = albumService.createAlbum(albumDto);

        assertTrue(Files.exists(Paths.get(Constants.PATH_PREFIX +"/photos/original/" + savedAlbumDto.getAlbumId())));
        assertTrue(Files.exists(Paths.get(Constants.PATH_PREFIX +"/photos/thumb/" + savedAlbumDto.getAlbumId())));
    }

    @Test
    void deleteAlbumDirectoriesTest() throws IOException {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setAlbumName("test");
        AlbumDto savedAlbumDto = albumService.deleteAlbum(albumDto);

        assertFalse(Files.exists(Paths.get(Constants.PATH_PREFIX +"/photos/original/" + savedAlbumDto.getAlbumId())));
        assertFalse(Files.exists(Paths.get(Constants.PATH_PREFIX +"/photos/thumb/" + savedAlbumDto.getAlbumId())));
    }

    @Test
    void testAlbumRepository() throws InterruptedException {
        Album album1 = new Album();
        Album album2 = new Album();

        album1.setAlbumName("aaaa");
        album2.setAlbumName("aaab");

        albumRepository.save(album1);
        TimeUnit.SECONDS.sleep(1);
        this.albumRepository.save(album2);

        List<Album> resDate = this.albumRepository.findByAlbumNameContainingOrderByCreatedAtDesc("aaa");
        assertEquals("aaab", resDate.get(0).getAlbumName());
        assertEquals("aaaa", resDate.get(1).getAlbumName());

        List<Album> resName = this.albumRepository.findByAlbumNameContainingOrderByAlbumNameAsc("aaa");
        assertEquals("aaaa", resName.get(0).getAlbumName());
        assertEquals("aaab", resName.get(1).getAlbumName());
    }

    @Test
    void changeNameTest() throws IOException {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setAlbumName("변경 전");
        AlbumDto res = albumService.createAlbum(albumDto);

        Long albumId = res.getAlbumId();
        AlbumDto updateDto = new AlbumDto();
        updateDto.setAlbumName("변경 후");
        this.albumService.changeName(albumId, updateDto);

        AlbumDto updatedDto = this.albumService.getAlbum(albumId);

        assertEquals("변경 후", updatedDto.getAlbumName());
    }

    @Test
    void deleteAlbumTest() throws IOException {
        Album album = new Album();
        album.setAlbumName("삭제될 앨범");
        AlbumDto deleteAlbum = this.albumService.createAlbum(AlbumMapper.convertToDto(album));

        this.albumService.deleteAlbum(deleteAlbum);

        assertFalse(Files.exists(Paths.get(Constants.PATH_PREFIX +"/photos/original/" + deleteAlbum.getAlbumId())));
        assertFalse(Files.exists(Paths.get(Constants.PATH_PREFIX + "/photos/thumb/" + deleteAlbum.getAlbumId())));

        assertThatThrownBy(() -> this.albumService.getAlbum("삭제될 앨범"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContainingAll("앨범 이름 "+deleteAlbum.getAlbumName()+"로 조회되지 않았습니다.");
    }
}