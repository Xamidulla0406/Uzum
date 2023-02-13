package uz.nt.uzumproject.service.mapper;

import uz.nt.uzumproject.model.Category;

public interface  CommonMapper<D,E>{
     D toDto(E e);
     E toEntity(D d);
}
