package com.sqarecross.photoalbum.service;

import com.sqarecross.photoalbum.Constants;
import com.sqarecross.photoalbum.domain.Album;
import com.sqarecross.photoalbum.domain.Photo;
import com.sqarecross.photoalbum.dto.PhotoDto;
import com.sqarecross.photoalbum.mapper.PhotoMapper;
import com.sqarecross.photoalbum.repository.AlbumRepository;
import com.sqarecross.photoalbum.repository.PhotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.persistence.EntityNotFoundException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@Slf4j
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final AlbumRepository albumRepository;
    private final String original_path = Constants.PATH_PREFIX + "/photos/original";
    private final String thumb_path = Constants.PATH_PREFIX + "/photos/thumb";


    public PhotoService(PhotoRepository photoRepository, AlbumRepository albumRepository) {
        this.photoRepository = photoRepository;
        this.albumRepository = albumRepository;
    }

    public PhotoDto getPhoto(Long albumId, Long photoId){
        Optional<Album> resAlbum = this.albumRepository.findById(albumId);
        Optional<Photo> resPhoto = this.photoRepository.findById(photoId);
        if(resAlbum.isPresent() && resPhoto.isPresent()){
            PhotoDto photoDto = PhotoMapper.convertToDto(resPhoto.get());
            return photoDto;
        }else {
            throw new EntityNotFoundException(String.format("사진 아이디 %d로 조회되지 않았습니다.", photoId));
        }
    }

    public PhotoDto savePhoto(MultipartFile file, Long albumId) {
        Optional<Album> res = this.albumRepository.findById(albumId);
        if(res.isEmpty()){
            throw new EntityNotFoundException("앨범이 존재하지 않습니다.");
        }
        String fileName = file.getOriginalFilename();
        int fileSize = (int) file.getSize();
        fileName = getCheckFileName(fileName,albumId);
        saveFile(file, albumId, fileName);

        Photo photo = new Photo();
        photo.setOriginalUrl("/photos/original/"+albumId+"/"+fileName);
        photo.setThumbUrl("/photos/thumb/" + albumId + "/" + fileName);
        photo.setFileName(fileName);
        photo.setFileSize(fileSize);
        photo.setAlbum(res.get());
        Photo createdPhoto = this.photoRepository.save(photo);
        return PhotoMapper.convertToDto(createdPhoto);
    }

    private String getCheckFileName(String fileName, Long albumId) {
        //주어진 path에서 확장자를 제거함
        String fileNameNoExt = StringUtils.stripFilenameExtension(fileName);
        //주어진 path에서 확장자 추출함
        String ext = StringUtils.getFilenameExtension(fileName);

        Optional<Photo> res = this.photoRepository.findByFileNameAndAlbum_AlbumId(fileName, albumId);

        //존재하는 파일이름이 있다면 파일이름(2).확장자 로 변경함
        int count = 2;
        while (res.isPresent()) {
            fileName = String.format("%s (%d).%s", fileNameNoExt, count, ext);
            res = this.photoRepository.findByFileNameAndAlbum_AlbumId(fileName, albumId);
            count++;
        }
        return fileName;
    }

    public void saveFile(MultipartFile file, Long albumId, String fileName) {
        try {
            if(!file.getContentType().contains("image")){
                throw new RuntimeException("Could not store the file. Error: "+e.getMessage());
            }
            String filePath = albumId + "/" + fileName;
            Files.copy(file.getInputStream(), Paths.get(original_path + "/" + filePath));

            BufferedImage thumbImg = Scalr.resize(ImageIO.read(file.getInputStream()), Constants.THUMB_SIZE, Constants.THUMB_SIZE);
            File thumbFile = new File(thumb_path + "/" + filePath);
            String ext = StringUtils.getFilenameExtension(fileName);
            if (ext == null) {
                throw new IllegalArgumentException(("No Extension"));
            }
            ImageIO.write(thumbImg, ext, thumbFile);
        }catch (Exception e){
            throw new RuntimeException("Could not store the file. Error: "+e.getMessage());
        }
    }
}
