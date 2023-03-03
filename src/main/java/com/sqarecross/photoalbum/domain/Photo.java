package com.sqarecross.photoalbum.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "photo")
@Getter
@Setter
public class Photo {
    @Id
    @Column(name = "photo_id", unique = true, nullable = false)
    @GeneratedValue
    private Long photoId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "album_id")
    private Album album;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "thumb_url")
    private String thumbUrl;

    @Column(name = "original_url")
    private String originalUrl;

    @Column(name = "file_size")
    private int fileSize;

    @Column(name = "uploaded_at")
    @CreationTimestamp
    private Date uploadedAt;
}
