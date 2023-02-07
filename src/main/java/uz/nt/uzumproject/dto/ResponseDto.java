package uz.nt.uzumproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto<T> {
    private Integer code;
    private String message;
    private boolean success;
    private T data;
}