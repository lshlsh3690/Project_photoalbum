package com.sqarecross.photoalbum.repository;

import com.sqarecross.photoalbum.domain.Album;
import com.sqarecross.photoalbum.dto.AlbumDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long>, AlbumCustomRepository {
    List<Album> findByAlbumNameContainingOrderByCreatedAtDesc(String keyword);

    List<Album> findByAlbumNameContainingOrderByAlbumNameAsc(String keyword);

    List<Album> findByAlbumNameContainingOrderByCreatedAtAsc(String keyword);

    List<Album> findByAlbumNameContainingOrderByAlbumNameDesc(String keyword);
}
