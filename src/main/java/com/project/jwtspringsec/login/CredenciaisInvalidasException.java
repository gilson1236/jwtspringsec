package com.project.jwtspringsec.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CredenciaisInvalidasException extends RuntimeException{
}