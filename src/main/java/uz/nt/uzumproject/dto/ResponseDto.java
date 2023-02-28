package uz.nt.uzumproject.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto<T> {

    private int code;
    private String message;
    private boolean success;
    private List<ErrorDto> errors;
    private T data;
    private List<ErrorDto> errors;
}