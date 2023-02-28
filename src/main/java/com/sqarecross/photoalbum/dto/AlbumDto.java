package com.sqarecross.photoalbum.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AlbumDto {
    private Long albumId;
    private String albumName;
    private Date createdAt;
    private int count;
    private List<String> thumbUrls;

}
