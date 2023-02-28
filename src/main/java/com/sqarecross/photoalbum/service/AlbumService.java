package com.sqarecross.photoalbum.service;

import com.sqarecross.photoalbum.Constants;
import com.sqarecross.photoalbum.domain.Album;
import com.sqarecross.photoalbum.domain.Photo;
import com.sqarecross.photoalbum.dto.AlbumDto;
import com.sqarecross.photoalbum.mapper.AlbumMapper;
import com.sqarecross.photoalbum.repository.AlbumRepository;
import com.sqarecross.photoalbum.repository.PhotoRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AlbumService {
    private AlbumRepository albumRepository;
    private PhotoRepository photoRepository;
    public AlbumService(AlbumRepository albumRepository, PhotoRepository photoRepository) {
        this.albumRepository = albumRepository;
        this.photoRepository = photoRepository;
    }

    public AlbumDto getAlbum(Long albumId){
        Optional<Album> res = albumRepository.findById(albumId);
        if(res.isPresent()){
            AlbumDto albumDto = AlbumMapper.convertToDto(res.get());
            albumDto.setCount(photoRepository.countByAlbum_AlbumId(albumId));
            return albumDto;
        } else {
            throw new EntityNotFoundException(String.format("앨범 아이디 %d로 조회되지 않았습니다.", albumId));
        }
    }

    public AlbumDto getAlbum(String albumName){
        try {
            Optional<Album> res = albumRepository.findByName(albumName);
            if(res.isPresent()){
                AlbumDto albumDto = AlbumMapper.convertToDto(res.get());
                albumDto.setCount(photoRepository.countByAlbum_AlbumId(res.get().getAlbumId()));
                return albumDto;
            } else {
                throw new EntityNotFoundException("앨범 이름 "+albumName+"로 조회되지 않았습니다.");
            }
        }catch (Exception e){
            throw new EntityNotFoundException("앨범 이름 "+albumName+"로 조회되지 않았습니다.");
        }
    }

    public AlbumDto createAlbum(AlbumDto albumDto) throws IOException {
        Album album = AlbumMapper.convertToModel(albumDto);
        this.albumRepository.save(album);
        this.createAlbumDirectories(album);
        return AlbumMapper.convertToDto(album);
    }

    private void createAlbumDirectories(Album album) throws IOException {
        Files.createDirectories(Paths.get(Constants.PATH_PREFIX +"/photos/original/" + album.getAlbumId()));
        Files.createDirectories(Paths.get(Constants.PATH_PREFIX +"/photos/thumb/" + album.getAlbumId()));
    }

    public AlbumDto deleteAlbum(AlbumDto albumDto) throws IOException {
        Album album = AlbumMapper.convertToModel(albumDto);
        this.albumRepository.delete(album);
        this.deleteAlbumDriectories(album);
        return AlbumMapper.convertToDto(album);
    }

    private void deleteAlbumDriectories(Album album) throws IOException {
        Path original_path = Paths.get(Constants.PATH_PREFIX + "/photos/original/" + album.getAlbumId());
        Path thumb_path = Paths.get(Constants.PATH_PREFIX + "/photos/thumb/" + album.getAlbumId());

        Stream<Path> originalStream = Files.walk(original_path);
        Stream<Path> thumbStream = Files.walk(thumb_path);

        List<Path> result = originalStream.filter(Files::isRegularFile).collect(Collectors.toList());

        for(Path path: result){
            Files.deleteIfExists(path);
        }

        List<Path> result2 = thumbStream.filter(Files::isRegularFile).collect(Collectors.toList());

        for(Path path: result2){
            Files.deleteIfExists(path);
        }

        Files.deleteIfExists(Paths.get(Constants.PATH_PREFIX + "/photos/original/" + album.getAlbumId()));
        Files.deleteIfExists(Paths.get(Constants.PATH_PREFIX + "/photos/thumb/" + album.getAlbumId()));
    }

    public List<AlbumDto> getAlbumList(String keyword, String sort, String orderBy){
        List<Album> albums = null;

        if(Objects.equals(orderBy,"asc")){
            albums = getAlbums(sort, albums, this.albumRepository.findByAlbumNameContainingOrderByAlbumNameAsc(keyword), this.albumRepository.findByAlbumNameContainingOrderByCreatedAtAsc(keyword));
        }else if(Objects.equals(orderBy,"desc")) {
            albums = getAlbums(sort, albums, this.albumRepository.findByAlbumNameContainingOrderByAlbumNameDesc(keyword), this.albumRepository.findByAlbumNameContainingOrderByCreatedAtDesc(keyword));
        }else {
            throw new IllegalArgumentException("알 수 없는 정렬 기준입니다.");
        }

        List<AlbumDto> albumDtos = AlbumMapper.convertToDtoList(albums);

        for(AlbumDto albumDto: albumDtos){
            List<Photo> top4 = this.photoRepository.findTop4ByAlbum_AlbumIdOrderByUploadedAtDesc(albumDto.getAlbumId());
            albumDto.setThumbUrls(top4.stream().map(Photo::getThumbUrl).map(c -> Constants.PATH_PREFIX+c).collect(Collectors.toList()));
        }
        return albumDtos;
    }

    private List<Album> getAlbums(String sort, List<Album> albums, List<Album> getByAlbumName, List<Album> getByAlbumDate) {
        if(Objects.equals(sort, "byName")){
            albums = getByAlbumName;
        }else if(Objects.equals(sort,"byDate")){
            albums = getByAlbumDate;
        }else {
            throw new IllegalArgumentException("알 수 없는 정렬 기준입니다.");
        }
        return albums;
    }

    public AlbumDto changeName(Long albumId, AlbumDto albumDto){
        Optional<Album> album = this.albumRepository.findById(albumId);
        checkAlbumExists(albumId, album);

        Album updateAlbum = album.get();
        updateAlbum.setAlbumName(albumDto.getAlbumName());
        Album savedAlbum = this.albumRepository.save(updateAlbum);
        return AlbumMapper.convertToDto(savedAlbum);
    }

    public void deleteAlbum(Long albumId) throws IOException {
        Optional<Album> album = this.albumRepository.findById(albumId);
        checkAlbumExists(albumId, album);

        this.albumRepository.delete(album.get());
        this.deleteAlbumDriectories(album.get());

        this.albumRepository.delete(album.get());
    }

    private void checkAlbumExists(Long albumId, Optional<Album> album) {
        if(album.isEmpty()){
            throw new NoSuchElementException("Album ID '"+ albumId +"'가 존재하지 않습니다");
        }
    }
}
