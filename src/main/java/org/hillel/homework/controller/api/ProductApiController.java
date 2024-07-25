package org.hillel.homework.controller.api;

import org.hillel.homework.dto.request.ProductRequest;
import org.hillel.homework.dto.response.ProductResponse;
import org.hillel.homework.entity.Product;
import org.hillel.homework.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable long id) {
        ProductResponse product = productService.getById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> add(@RequestBody ProductRequest product) {
        ProductResponse createdProduct = productService.save(product);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable long id, @RequestBody ProductRequest product) {
        ProductResponse updatedProduct = productService.update(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
