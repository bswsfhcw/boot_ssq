package com.winning.batjx.core.common.handler;

import com.winning.jbase.common.domain.ResponseMessage;
import com.winning.jbase.common.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Edward
 */
@RestControllerAdvice
public class BizExceptionHandler {

    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMessage handleUnauthenticatedException(ApiException e) {
        ResponseMessage message = new ResponseMessage();
        message.setCode("F").setMessage(e.getMessage());
        return message;
    }
}
