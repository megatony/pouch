package com.akgul.kangroo.pouch.api.model;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundPouchApiResponse extends PouchApiResponse {
    public NotFoundPouchApiResponse(String message) {
        LoggerFactory.getLogger(NotFoundPouchApiResponse.class).error(message);
        this.setMessage("ERROR : " + message);
    }
}
