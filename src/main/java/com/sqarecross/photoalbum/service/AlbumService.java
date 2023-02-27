package com.sqarecross.photoalbum.service;

import com.sqarecross.photoalbum.domain.Album;
import com.sqarecross.photoalbum.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class AlbumService {
    private AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public Album getAlbum(Long albumId){
        Optional<Album> res = albumRepository.findById(albumId);
        if(res.isPresent()){
            return res.get();
        } else {
            throw new EntityNotFoundException(String.format("앨범 아이디 %d로 조회되지 않았습니다.", albumId));
        }
    }

    public Album getAlbum(String albumName){
        Optional<Album> res = albumRepository.findByName(albumName);
        if(res.isPresent()){
            return res.get();
        } else {
            throw new EntityNotFoundException(String.format("앨범 이름 %s로 조회되지 않았습니다.", albumName));
        }
    }
}
