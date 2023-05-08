package org.example.Exceptions;

import org.example.Exceptions.models.BasicException;
import org.example.Exceptions.models.ResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BasicExceptionHandler {

    private final Logger LOG = LoggerFactory.getLogger(ExceptionHandler.class);

    @ExceptionHandler({BasicException.class})
    public ResponseEntity<ResponseError> handleBasicExceptions(BasicException basicException){
        LOG.error(basicException.getMessage());
        ResponseError responseError = new ResponseError();
        responseError.setMessage(basicException.getMessage());
        return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
    }
}
