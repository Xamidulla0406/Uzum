package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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

import static uz.nt.uzumproject.service.validator.AppStatusMessages.OK;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    public ResponseDto<Image> saveImage(MultipartFile file) {
        Image image=new Image();
        image.setName(file.getOriginalFilename());
        image.setExtension(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        image.setCreatedAt(LocalDateTime.now());

        try {
            String filePath;
            Files.copy(file.getInputStream(), Path.of(filePath=filePath(image.getExtension())));
            image.setUrl(filePath);
            imageRepository.save(image);
            return ResponseDto.<Image>builder()
                    .code(0)
                    .message(OK)
                    .data(image)
                    .success(true)
                    .build();

        } catch (IOException e) {

            return ResponseDto.<Image>builder()
                    .code(-2)
                    .success(false)
                    .message("Error while saving file: "+e.getMessage())
                    .build();
        }

    }

    private String filePath(String ext){
        LocalDate localDate=LocalDate.now();
        String path = localDate.format(DateTimeFormatter.ofPattern("yyyy/MMM/dd"));
        File file=new File("upload/"+path);
        if(!file.exists()){
            file.mkdirs();
        }
        return file.getPath()+"\\"+System.currentTimeMillis()+ext;
    }
}
