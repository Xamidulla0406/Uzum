package uz.nt.uzumproject.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.nt.uzumproject.dto.ErrorDto;
import uz.nt.uzumproject.dto.ResponseDto;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@RestControllerAdvice
public class ExceptionHandlerResource {


    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> validationError(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest()
                .body(ResponseDto.<Void>builder()
                        .message(VALIDATION_ERROR)
                        .code(VALIDATION_ERROR_CODE)
                        .errors(e.getBindingResult().getFieldErrors()
                                .stream()
                                .map(f -> new ErrorDto(f.getField(),f.getDefaultMessage()))
                                .collect(toList())).build());

    }


}
