package ru.netology.diplomacloudstorage.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.netology.diplomacloudstorage.dto.StorageError;
import ru.netology.diplomacloudstorage.exceptions.DataValidationEx;
import ru.netology.diplomacloudstorage.exceptions.FileNotFoundEx;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExController {

    private static final Logger LOGGER = Logger.getLogger(ExController.class);

    @ExceptionHandler(UsernameNotFoundException.class)
    public StorageError handleUsernameNotFoundException(HttpServletRequest request, UsernameNotFoundException e) {
        LOGGER.error(request.getRequestURI() + ": UsernameNotFoundException: " + e.getMessage());

        StorageError error = new StorageError();
        error.setMessage(e.getMessage());
        error.setId(HttpStatus.BAD_REQUEST.value());
        return error;
    }

    @ExceptionHandler(FileNotFoundEx.class)
    public StorageError handleCloudFileNotFoundException(HttpServletRequest request, FileNotFoundEx e) {
        LOGGER.error(request.getRequestURI() + ": FileNotFoundException: " + e.getMessage());

        StorageError error = new StorageError();
        error.setMessage(e.getMessage());
        error.setId(HttpStatus.BAD_REQUEST.value());
        return error;
    }

    @ExceptionHandler(DataValidationEx.class)
    public StorageError handleDataValidationException(HttpServletRequest request, DataValidationEx e) {
        LOGGER.error(request.getRequestURI() + ": DataValidationException: " + e.getMessage());

        StorageError error = new StorageError();
        error.setMessage(e.getMessage());
        error.setId(HttpStatus.BAD_REQUEST.value());
        return error;
    }

    @ExceptionHandler(value = {ConstraintViolationException.class,
            MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class, MethodArgumentNotValidException.class})
    public StorageError handleInputDataException(HttpServletRequest request, Exception e) {
        LOGGER.error(request.getRequestURI() + ": InputDataException: " + e.getMessage());

        StorageError error = new StorageError();
        error.setMessage(e.getMessage());
        error.setId(HttpStatus.BAD_REQUEST.value());
        return error;
    }
}
