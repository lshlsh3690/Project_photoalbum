package com.sqarecross.photoalbum.repository;


import com.sqarecross.photoalbum.domain.Album;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

public interface AlbumCustomRepository {
    Optional<Album> findByName(String albumName);
}
