package net.pixelplugins.bff.configuration;

import feign.codec.ErrorDecoder;
import net.pixelplugins.bff.handler.UserClientErrorDecoder;
import org.springframework.context.annotation.Bean;

public class UserClientConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new UserClientErrorDecoder();
    }

}
