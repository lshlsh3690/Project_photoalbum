package com.sqarecross.photoalbum.controller;

import com.sqarecross.photoalbum.domain.Album;
import com.sqarecross.photoalbum.dto.AlbumDto;
import com.sqarecross.photoalbum.service.AlbumService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/albums")
public class AlbumController {
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/{albumId}")
    public ResponseEntity<AlbumDto> getAlbum(@PathVariable("albumId") final long albumId) {
        AlbumDto album = this.albumService.getAlbum(albumId);
        return ResponseEntity.ok(album);
    }

    @GetMapping("/query")
    public ResponseEntity<AlbumDto>getAlbumByQuery(@RequestParam Long albumId){
        AlbumDto album = this.albumService.getAlbum(albumId);
        return ResponseEntity.ok(album);
    }

    @PostMapping("/json_body")
    public ResponseEntity<AlbumDto>getAlbumByJson(@RequestBody AlbumDto albumDto){
        AlbumDto resDto = this.albumService.getAlbum(albumDto.getAlbumId());
        return ResponseEntity.ok(resDto);
    }

    @PostMapping
    public ResponseEntity<AlbumDto>createAlbum(@RequestBody final AlbumDto albumDto) throws IOException {
        AlbumDto savedAlbumDto = this.albumService.createAlbum(albumDto);
        return ResponseEntity.ok(savedAlbumDto);
    }

}
