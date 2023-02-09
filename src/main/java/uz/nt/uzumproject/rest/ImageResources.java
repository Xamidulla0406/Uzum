package uz.nt.uzumproject.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.uzumproject.dto.ImageDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Image;
import uz.nt.uzumproject.service.ImageService;

@RequiredArgsConstructor
@RestController
@RequestMapping("image")
public class ImageResources {
    private final ImageService service;

    @PostMapping
    public ResponseDto<Image> uploadImage(@RequestBody MultipartFile image){
        return service.saveImage(image);
    }

}
