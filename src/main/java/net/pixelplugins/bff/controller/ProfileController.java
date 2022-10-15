package net.pixelplugins.bff.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.pixelplugins.bff.client.ProductClient;
import net.pixelplugins.bff.client.UserClient;
import net.pixelplugins.commons.product.dto.response.ReadProductResponse;
import net.pixelplugins.commons.user.dto.response.ReadUserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000/", allowCredentials = "true")
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/public/user")
public class ProfileController {

    private final UserClient client;
    private final ProductClient productClient;

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ReadUserResponse me(@AuthenticationPrincipal User user) {
        var response = client.findByUsername(user.getUsername());
        var products = productClient.findAll();
        var productsDto = products.stream()
                .map(ReadProductResponse::toDTO)
                .toList();

        response.setProducts(productsDto);

        return response;
    }

}
