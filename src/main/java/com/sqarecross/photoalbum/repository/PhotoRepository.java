package com.sqarecross.photoalbum.repository;

import com.sqarecross.photoalbum.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    int countByAlbum_AlbumId(Long albumId);

    List<Photo> findTop4ByAlbum_AlbumIdOrderByUploadedAtDesc(Long albumId);

    Optional<Photo> findByFileNameAndAlbum_AlbumId(String photoName, Long albumId);

    List<Photo> findAllByFileNameContainingAndAlbum_AlbumIdOrderByUploadedAtDesc(String keyword, Long albumId);

    List<Photo> findAllByFileNameContainingAndAlbum_AlbumIdOrderByFileNameDesc(String keyword, Long albumId);
}
