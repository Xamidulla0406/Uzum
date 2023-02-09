package uz.nt.uzumproject.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {

    private Integer id;
    private String name;
    private String extension;
    private MultipartFile file;
}
