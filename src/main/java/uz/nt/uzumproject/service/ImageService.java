package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.uzumproject.dto.ImageDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Image;
import uz.nt.uzumproject.repository.ImageRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final ImageRepository imageRepository;
    public ResponseDto<Image> saveImage(MultipartFile multipartFile) {
        Image image = new Image();

        image.setName(multipartFile.getOriginalFilename());
        image.setExtension(multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")));
        image.setCreatedAt(LocalDateTime.now());

        try {
            String filaPath;
            Files.copy(multipartFile.getInputStream(), Path.of(filaPath = filePath(image.getExtension())));
            image.setUrl(filaPath);
            imageRepository.save(image);

            return ResponseDto.<Image>builder()
                    .message("OK")
                    .success(true)
                    .data(image)
                    .build();

        } catch (IOException e) {
            log.error("Error while saving image: " + e.getMessage());
            return ResponseDto.<Image>builder()
                    .message("Error saving image: " + e.getMessage())
                    .code(2)
                    .build();
        }
    }

    private String filePath(String extension) {
        LocalDate localDate = LocalDate.now();
        String path = localDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        File file = new File("upload/" + path);

        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getPath() + "\\" + System.currentTimeMillis() +extension;
    }
}
