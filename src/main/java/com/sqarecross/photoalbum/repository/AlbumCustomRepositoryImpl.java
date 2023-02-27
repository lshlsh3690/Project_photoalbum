package com.sqarecross.photoalbum.repository;


import com.sqarecross.photoalbum.domain.Album;
import com.sqarecross.photoalbum.dto.AlbumDto;

import javax.persistence.EntityManager;
import java.util.Optional;

public class AlbumCustomRepositoryImpl implements AlbumCustomRepository {
    private final EntityManager entityManager;

    public AlbumCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Album> findByName(String albumName) {
        return Optional.ofNullable(entityManager.createQuery("select m from Album m", Album.class).getSingleResult());
    }
}
