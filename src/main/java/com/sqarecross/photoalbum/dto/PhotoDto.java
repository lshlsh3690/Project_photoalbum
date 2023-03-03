package com.sqarecross.photoalbum.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PhotoDto {
    private Long photoId;
    private String fileName;
    private int fileSize;
    private String originalUrl;
    private String thumbUrl;
    private Date uploadedAt;
    private Long albumId;
}
