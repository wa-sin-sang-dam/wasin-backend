package com.wasin.backend._core.exception;

import com.wasin.backend._core.exception.error.*;
import com.wasin.backend._core.util.ApiUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validationException(MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return response(ApiUtils.error(errors.get(0).getDefaultMessage(), status), status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> messageNotReadable() {
        BaseException e = BaseException.MESSAGE_NOT_READABLE;
        return response(error(e), e.getStatus());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> argumentTypeException() {
        BaseException e = BaseException.INVALID_METHOD_ARGUMENTS;
        return response(error(e), e.getStatus());
    }

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public ResponseEntity<?> databaseException(){
        BaseException e = BaseException.DATABASE_ERROR;
        return response(error(e), e.getStatus());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentException() {
        BaseException e = BaseException.INVALID_ARGUMENT;
        return response(error(e), e.getStatus());
    }

    @ExceptionHandler({BadRequestException.class, UnauthorizedException.class, ForbiddenException.class,
            NotFoundException.class, ServerException.class})
    public ResponseEntity<?> customException(CustomException e){
        return response(e.body(), e.status());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unknownServerError(Exception e){
        e.printStackTrace();
        BaseException be = BaseException.UNEXPECTED_EXCEPTION;
        return response(error(be), be.getStatus());
    }

    private <T> ResponseEntity<T> response(T body, HttpStatus status) {
        return new ResponseEntity<>(body, status);
    }

    private static ApiUtils.ApiResult<?> error(BaseException e) {
        return ApiUtils.error(e.getMessage(), e.getStatus());
    }
}
