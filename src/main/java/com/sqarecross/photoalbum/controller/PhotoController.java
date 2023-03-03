package com.sqarecross.photoalbum.controller;

import com.sqarecross.photoalbum.dto.PhotoDto;
import com.sqarecross.photoalbum.service.PhotoService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/albums/{albumId}/photos")
public class PhotoController {
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/{photoId}")
    public ResponseEntity<PhotoDto> getPhotoInfo(@PathVariable("albumId") final Long albumId,
                                                 @PathVariable("photoId") final Long photoId) {
        PhotoDto photoDto = this.photoService.getPhoto(albumId, photoId);
        return ResponseEntity.ok(photoDto);
    }

    @PostMapping
    public ResponseEntity<List<PhotoDto>>uploadPhotos(@PathVariable("albumId")final Long albumId,
                                                      @RequestParam("photos")MultipartFile[] files) {
        List<PhotoDto>photos = new ArrayList<>();
        for(MultipartFile file : files){
            PhotoDto photoDto = this.photoService.savePhoto(file,albumId);
            photos.add(photoDto);
        }
        return ResponseEntity.ok(photos);
    }

    @GetMapping("/download")
    public void downloadPhotos(@RequestParam("photoIds")Long [] photoIds,
                               HttpServletResponse response){
        try{
            if(photoIds.length == 1){
                File file = this.photoService.getImageFile(photoIds[0]);
                OutputStream outputStream = response.getOutputStream();
                IOUtils.copy(new FileInputStream(file), outputStream);
                outputStream.close();
            }else{
                response.setContentType("application/zip");

                ArrayList<File>files = new ArrayList<>();
                for(long photoId : photoIds){
                    File file = this.photoService.getImageFile(photoId);
                    files.add(file);
                }
                ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
                for (File file : files) {
//                    FileInputStream fileInputStream = new FileInputStream(file);
                    FileSystemResource resource = new FileSystemResource(file);
                    ZipEntry e = new ZipEntry(resource.getFilename());
                    e.setSize(resource.contentLength());
                    zipOutputStream.putNextEntry(e);
                    StreamUtils.copy(resource.getInputStream(), zipOutputStream);
                }
                zipOutputStream.close();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("error");
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }


}
