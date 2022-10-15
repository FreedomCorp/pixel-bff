package net.pixelplugins.bff.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Nenhum usuário com essa senha foi encontrado")
public class PasswordNotMatchException extends Exception {
}
