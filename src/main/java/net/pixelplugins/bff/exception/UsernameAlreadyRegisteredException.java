package net.pixelplugins.bff.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Esse nome de usuário já foi registrado em nosso sistema")
public class UsernameAlreadyRegisteredException extends Exception {

}