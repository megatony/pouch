package com.akgul.kangroo.pouch.api.model;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictPouchApiResponse extends PouchApiResponse {

    public ConflictPouchApiResponse(String message) {
        LoggerFactory.getLogger(ConflictPouchApiResponse.class).warn(message);
        this.setMessage("BUSINESS ERROR : " + message);}
}
