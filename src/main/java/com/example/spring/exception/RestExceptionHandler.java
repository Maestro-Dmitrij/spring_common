package com.example.spring.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.nio.file.AccessDeniedException;
import java.util.Collections;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ErrorMessage> handleException(Exception ex) {
        log.error("Серверная ошибка", ex);
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.addError(ex.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(value = {ExecutionConflictException.class})
    protected ResponseEntity<ErrorMessage> handleExecutionConflictException(
            ExecutionConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getErrMsg());
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(value = {ValidationException.class})
    protected ResponseEntity<ErrorMessage> handleValidationException(
            ValidationException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.addErrors(Collections.singletonList(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(value = {NotFoundObjectException.class})
    protected ResponseEntity<ErrorMessage> handleNotFoundObjectException(
            NotFoundObjectException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.addError(ex.getMessage());
        return ResponseEntity.status(NOT_FOUND).body(errorMessage);
    }

/*    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(value = {AccessDeniedException.class})
    protected ResponseEntity<ErrorMessage> handleAccessDeniedException() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.addError("Доступ запрещён");
        return ResponseEntity.status(FORBIDDEN).body(errorMessage);
    }*/

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<ErrorMessage> handleMethodArgumentTypeMismatchException() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.addError("Ошибка запроса");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

/*    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(value = {AuthAccessDeniedException.class})
    protected ResponseEntity<ErrorMessage> handleAuthAccessDeniedException(
            AuthAccessDeniedException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.addError(ex.getMessage());
        return ResponseEntity.status(UNAUTHORIZED).body(errorMessage);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    protected ResponseEntity<ErrorMessage> handleBadCredentialsException() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.addError("Неверные имя пользователя или пароль");
        return ResponseEntity.status(UNAUTHORIZED).body(errorMessage);
    }

    @ExceptionHandler(value = {LockedException.class})
    protected ResponseEntity<ErrorMessage> handleLockedException() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.addError("Пользователь заблокирован. Обратитесь к Администратору");
        return ResponseEntity.status(UNAUTHORIZED).body(errorMessage);
    }*/

/*    @ResponseStatus(CONFLICT)
    @ExceptionHandler(value = {MaxUploadSizeExceededException.class})
    protected ResponseEntity<ErrorMessage> handleMaxUploadSizeExceededException() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.addError("Превышен максимальный размер загружаемых данных");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }*/

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<ErrorMessage> handlePlatformNotValidFieldException(
            ConstraintViolationException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.addErrors(ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList());
        return ResponseEntity.status(BAD_REQUEST).body(errorMessage);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> onMethodArgumentNotValid(MethodArgumentNotValidException e) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.addErrors(e.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList());
        return ResponseEntity.status(BAD_REQUEST).body(errorMessage);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> onIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(BAD_REQUEST).body(new ErrorMessage(e.getMessage()));
    }
}