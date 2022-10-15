package net.pixelplugins.bff.controller;

import lombok.AllArgsConstructor;
import net.pixelplugins.bff.client.CategoryClient;
import net.pixelplugins.commons.category.dto.request.CreateCategoryRequest;
import net.pixelplugins.commons.category.dto.response.CreateCategoryResponse;
import net.pixelplugins.commons.category.dto.response.ReadCategoryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/", allowCredentials = "true")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/public/category")
public class CategoryController {

    private final CategoryClient client;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CreateCategoryResponse create(@RequestBody CreateCategoryRequest request) {
        return client.create(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReadCategoryResponse findById(@PathVariable long id) {
        return client.findById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ReadCategoryResponse> findAll() {
        return client.findAll();
    }

}
