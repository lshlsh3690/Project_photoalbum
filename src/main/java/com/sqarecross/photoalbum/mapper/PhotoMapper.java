package com.sqarecross.photoalbum.mapper;

import com.sqarecross.photoalbum.domain.Photo;
import com.sqarecross.photoalbum.dto.PhotoDto;

public class PhotoMapper {
    public static PhotoDto convertToDto(Photo photo){
        PhotoDto photoDto = new PhotoDto();
        photoDto.setPhotoId(photo.getPhotoId());
        photoDto.setFileName(photo.getFileName());
        photoDto.setFileSize(photoDto.getFileSize());
        photoDto.setThumbUrl(photo.getThumbUrl());
        photoDto.setOriginalUrl(photo.getOriginalUrl());
        photoDto.setUploadedAt(photo.getUploadedAt());
        photoDto.setAlbumId(photoDto.getAlbumId());

        return photoDto;
    }
}
