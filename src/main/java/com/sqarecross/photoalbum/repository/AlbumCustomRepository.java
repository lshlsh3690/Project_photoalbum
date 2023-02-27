package com.sqarecross.photoalbum.repository;


import com.sqarecross.photoalbum.domain.Album;

import java.util.Optional;

public interface AlbumCustomRepository {
    Optional<Album> findByName(String albumName);
}
