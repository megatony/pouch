package com.akgul.kangaroo.pouch.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PouchApiResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
}
