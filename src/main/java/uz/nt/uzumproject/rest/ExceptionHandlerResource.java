package uz.nt.uzumproject.rest;

import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.nt.uzumproject.dto.ErrorDto;
import uz.nt.uzumproject.dto.ResponseDto;

import java.util.stream.Collectors;

import static uz.nt.uzumproject.service.validator.AppStatusCodes.VALIDATION_ERROR_CODE;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.VALIDATION_ERROR;

@RestControllerAdvice
public class ExceptionHandlerResource {
    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> validationError(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest()
                .body(ResponseDto.<Void>builder()
                        .code(VALIDATION_ERROR_CODE)
                        .message(VALIDATION_ERROR)
                        .errors(e.getBindingResult().getFieldErrors()
                                .stream().map(f-> new ErrorDto(f.getField(), f.getDefaultMessage()))
                                    .collect(Collectors.toList()))
                        .build());
    }
    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> signatureError(SignatureException e){
        return ResponseEntity.badRequest()
                .body(ResponseDto.<Void>builder()
                        .code(VALIDATION_ERROR_CODE)
                        .message(VALIDATION_ERROR)
                        .build());
    }
}
