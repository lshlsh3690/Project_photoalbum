package com.sqarecross.photoalbum.mapper;

import com.sqarecross.photoalbum.domain.Album;
import com.sqarecross.photoalbum.dto.AlbumDto;

public class AlbumMapper {
    public static AlbumDto convertToDto(Album album){
        AlbumDto albumDto = new AlbumDto();
        albumDto.setAlbumId(album.getAlbumId());
        albumDto.setAlbumName(album.getAlbumName());
        albumDto.setCreatedAt(album.getCreatedAt());
        albumDto.setCount(albumDto.getCount());
        return albumDto;
    }

    public static Album convertToModel(AlbumDto albumDto){
        Album album = new Album();
        album.setAlbumId(albumDto.getAlbumId());
        album.setAlbumName(albumDto.getAlbumName());
        album.setCreatedAt(albumDto.getCreatedAt());
        return album;
    }
}
