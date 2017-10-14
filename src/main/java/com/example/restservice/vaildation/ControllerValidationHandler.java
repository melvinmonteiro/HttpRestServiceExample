package com.example.restservice.vaildation;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * A custom validation handler
 * Created by melvin on 10/12/17.
 */
@ControllerAdvice
public class ControllerValidationHandler {

    @ExceptionHandler(RestExampleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MessageDTO serverError(RestExampleException ex) {
        MessageDTO errorMessage = new MessageDTO();
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setType(MessageType.ERROR);
        return errorMessage;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MessageDTO httpMessageError(HttpMessageNotReadableException ex) {
        MessageDTO errorMessage = new MessageDTO();
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setType(MessageType.ERROR);
        return errorMessage;
    }
}
