package uz.nt.uzumproject.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uz.nt.uzumproject.dto.ImageDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.service.ImageService;

@RequiredArgsConstructor
@RestController
@RequestMapping("image")
public class ImageResources {
    private final ImageService service;

    @PostMapping
    public ResponseDto<ImageDto> uploadImage(ImageDto imageDto){
        return service.saveImage(imageDto);
    }

}
