package net.pixelplugins.bff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PixelBffServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PixelBffServiceApplication.class, args);
    }

}
