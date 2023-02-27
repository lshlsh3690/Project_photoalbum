package com.sqarecross.photoalbum.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AlbumDto {
    private Long albumId;
    private String albumName;
    private Date createdAt;
    private int count;
}
