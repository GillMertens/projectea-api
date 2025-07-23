package com.projectea.projectea.exceptions.handling;

import org.springframework.http.HttpStatus;

public record ExceptionResponse(String message, HttpStatus status) {

}
