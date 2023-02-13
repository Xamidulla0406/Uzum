package uz.nt.uzumproject.service.mapper;

import org.mapstruct.Mapper;

public interface CommonMapper <D, E>{
    D toDto(E e);
    E toEntity(D d);
}
