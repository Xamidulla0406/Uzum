package uz.nt.uzumproject.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.nt.uzumproject.dto.ErrorDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.service.validator.AppStatusCodes;
import uz.nt.uzumproject.service.validator.AppStatusMessages;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class ExceptionHandlerResources {
    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> validationError(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest()
                .body(ResponseDto.<Void>builder()
                        .code(AppStatusCodes.OK_CODE)
                        .message(AppStatusMessages.VALIDATION_ERROR)
                        .errors(e.getBindingResult().getFieldErrors().stream()
                                .map(f-> new ErrorDto(f.getField(),f.getDefaultMessage()))
                                .collect(toList()))
                        .build());
    }
}
