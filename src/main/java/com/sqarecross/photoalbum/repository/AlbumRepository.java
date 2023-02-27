package com.sqarecross.photoalbum.repository;

import com.sqarecross.photoalbum.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long>, AlbumCustomRepository {

}
