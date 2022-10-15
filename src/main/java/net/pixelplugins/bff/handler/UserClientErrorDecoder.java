package net.pixelplugins.bff.handler;

import feign.Response;
import feign.codec.ErrorDecoder;
import net.pixelplugins.bff.exception.UsernameAlreadyRegisteredException;
import org.springframework.http.HttpStatus;

public class UserClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String method, Response response) {
        var responseStatus = HttpStatus.valueOf(response.status());

        if (responseStatus == HttpStatus.CONFLICT) {
            return new UsernameAlreadyRegisteredException();
        } else {
            return new Exception("Algum erro desconhecido ocorreu");
        }
    }
}
