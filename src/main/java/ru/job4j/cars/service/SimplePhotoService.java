package ru.job4j.cars.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.PhotoDto;
import ru.job4j.cars.model.Photo;
import ru.job4j.cars.repository.PhotoRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Service
public class SimplePhotoService implements PhotoService {
    private final PhotoRepository photoRepository;
    private final String storageDirectory;

    public SimplePhotoService(PhotoRepository photoRepository,
                             @Value("${file.directory}") String storageDirectory) {
        this.photoRepository = photoRepository;
        this.storageDirectory = storageDirectory;
        createStorageDirectory(storageDirectory);
    }

    private void createStorageDirectory(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Photo save(PhotoDto photoDto) {
        var path = getNewPhotoPath(photoDto.getPath());
        writePhotoBytes(path, photoDto.getContent());
        Photo photo = new Photo();
        photo.setPath(path);
        return photoRepository.save(photo);
    }

    private String getNewPhotoPath(String sourceName) {
        return storageDirectory + java.io.File.separator + UUID.randomUUID() + sourceName;
    }

    private void writePhotoBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<PhotoDto> getPhotoById(int id) {
        var photoOptional = photoRepository.findById(id);
        if (photoOptional.isEmpty()) {
            return Optional.empty();
        }
        var content = readPhotoAsBytes(photoOptional.get().getPath());
        return Optional.of(new PhotoDto(photoOptional.get().getPath(), content));
    }

    private byte[] readPhotoAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(int id) {
        var photoOptional = photoRepository.findById(id);
        if (photoOptional.isEmpty()) {
            return false;
        }
        deletePhoto(photoOptional.get().getPath());
        return photoRepository.deleteById(id);
    }

    private void deletePhoto(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
