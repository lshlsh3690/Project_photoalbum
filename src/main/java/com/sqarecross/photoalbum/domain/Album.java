package com.sqarecross.photoalbum.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "album")
@NoArgsConstructor
@Getter
@Setter
public class Album {
    @Id
    @GeneratedValue
    @Column(name = "album_id", unique = true, nullable = false)
    private Long albumId;

    @Column(name = "album_name", unique = false, nullable = false)
    private String albumName;

    @Column(name = "created_at", unique = false, nullable = true)
    @CreationTimestamp
    private Date createdAt;
}
