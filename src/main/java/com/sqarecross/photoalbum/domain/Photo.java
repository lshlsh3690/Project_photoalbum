package com.sqarecross.photoalbum.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @Column(name = "photo_id", unique = true, nullable = false)
    @GeneratedValue
    private Long photoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "thumb_url")
    private String thumbUrl;

    @Column(name = "original_url")
    private String originalUrl;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "uploaded_at")
    @CreationTimestamp
    private Date uploadedAt;
}
