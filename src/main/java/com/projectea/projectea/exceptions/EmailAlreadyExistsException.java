package com.projectea.projectea.exceptions;

import com.projectea.projectea.exceptions.handling.BaseRuntimeException;
import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends BaseRuntimeException {
    private static final String BASE_MESSAGE = "A user with this email already exists.";
    private static final HttpStatus BASE_HTTP_STATUS = HttpStatus.CONFLICT;

    public EmailAlreadyExistsException() {
        super(BASE_MESSAGE, BASE_HTTP_STATUS);
    }
}
