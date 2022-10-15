package net.pixelplugins.bff.controller;

import lombok.AllArgsConstructor;
import net.pixelplugins.bff.client.ProductClient;
import net.pixelplugins.commons.product.dto.request.CreateProductRequest;
import net.pixelplugins.commons.product.dto.response.ReadProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/", allowCredentials = "true")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/public/products")
public class ProductController {

    private final ProductClient client;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ReadProductResponse create(@RequestBody CreateProductRequest request) {
        return client.create(request);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ReadProductResponse> findAll() {
        return client.findAll();
    }

}
