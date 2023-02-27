package com.sqarecross.photoalbum.service;

import com.sqarecross.photoalbum.domain.Album;
import com.sqarecross.photoalbum.dto.AlbumDto;
import com.sqarecross.photoalbum.mapper.AlbumMapper;
import com.sqarecross.photoalbum.repository.AlbumRepository;
import com.sqarecross.photoalbum.repository.PhotoRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class AlbumService {
    private AlbumRepository albumRepository;
    private PhotoRepository photoRepository;
    public AlbumService(AlbumRepository albumRepository, PhotoRepository photoRepository) {
        this.albumRepository = albumRepository;
        this.photoRepository = photoRepository;
    }

    public AlbumDto getAlbum(Long albumId){
        Optional<Album> res = albumRepository.findById(albumId);
        if(res.isPresent()){
            AlbumDto albumDto = AlbumMapper.convertToDto(res.get());
            albumDto.setCount(photoRepository.countByAlbum_AlbumId(albumId));
            return albumDto;
        } else {
            throw new EntityNotFoundException(String.format("앨범 아이디 %d로 조회되지 않았습니다.", albumId));
        }
    }

    public AlbumDto getAlbum(String albumName){
        Optional<Album> res = albumRepository.findByName(albumName);
        if(res.isPresent()){
            AlbumDto albumDto = AlbumMapper.convertToDto(res.get());
            albumDto.setCount(photoRepository.countByAlbum_AlbumId(res.get().getAlbumId()));
            return albumDto;
        } else {
            throw new EntityNotFoundException("앨범 이름 "+albumName+"로 조회되지 않았습니다.");
        }
    }
}
