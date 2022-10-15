package net.pixelplugins.bff.client;

import net.pixelplugins.commons.category.dto.request.CreateCategoryRequest;
import net.pixelplugins.commons.category.dto.response.CreateCategoryResponse;
import net.pixelplugins.commons.category.dto.response.ReadCategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "category-service", url = "http://localhost:8083/api/v1/category")
public interface CategoryClient {

    @PostMapping
    CreateCategoryResponse create(@RequestBody CreateCategoryRequest request);

    @GetMapping("/{id}")
    ReadCategoryResponse findById(@PathVariable long id);

    @GetMapping("/all")
    List<ReadCategoryResponse> findAll();

}
