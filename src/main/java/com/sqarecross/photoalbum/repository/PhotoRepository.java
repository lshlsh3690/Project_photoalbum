package com.sqarecross.photoalbum.repository;

import com.sqarecross.photoalbum.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    int countByAlbum_AlbumId(Long albumId);

    List<Photo> findTop4ByAlbum_AlbumIdOrderByUploadedAtDesc(Long albumId);
}
