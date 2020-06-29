package com.akgul.kangaroo.pouch.api.model;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class MethodNotAllowedPouchApiResponse extends PouchApiResponse {
    public MethodNotAllowedPouchApiResponse(String message) {
        LoggerFactory.getLogger(MethodNotAllowedPouchApiResponse.class).error(message);
        this.setMessage(message);
    }
}
