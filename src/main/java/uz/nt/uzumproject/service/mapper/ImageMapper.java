package uz.nt.uzumproject.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.uzumproject.dto.ImageDto;
import uz.nt.uzumproject.model.Image;

@Mapper(componentModel = "spring")
public abstract class ImageMapper {

    public abstract ImageDto toDto(Image image);
}
