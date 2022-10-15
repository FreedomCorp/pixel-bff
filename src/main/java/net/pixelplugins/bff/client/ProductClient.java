package net.pixelplugins.bff.client;

import net.pixelplugins.commons.product.dto.request.CreateProductRequest;
import net.pixelplugins.commons.product.dto.response.ReadProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name= "product-service", url = "http://localhost:8083/api/v1/product")
public interface ProductClient {

    @PostMapping
    ReadProductResponse create(@RequestBody CreateProductRequest request);

    @GetMapping("/{id}")
    ReadProductResponse findById(@PathVariable long id);

    @GetMapping("/all")
    List<ReadProductResponse> findAll();
}
