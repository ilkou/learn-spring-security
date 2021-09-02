package io.github.ilkou.learnspringsecurity.web.rest;

import io.github.ilkou.learnspringsecurity.domain.Product;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private static final List<Product> PRODUCTS = Arrays.asList(
            new Product(1, "Ilkou Oussama", 130.5f),
            new Product(2, "Designer Achraf", 150.0f)
    );

    @GetMapping
    @PreAuthorize("hasAuthority('product:read')")
    public List<Product> getProducts() {
        System.out.println("GET PRODUCTS");
        return PRODUCTS;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('product:write')")
    public void postProduct(@RequestBody Product product) {
        System.out.println("POST PRODUCT: " + product);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('product:write')")
    public void putProduct(@PathVariable("id") Integer id, @RequestBody Product product) {
        System.out.println("PUT PRODUCTS{"+id+"}: " + product );
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('product:write')")
    public void deleteProduct(@PathVariable("id") Integer id) {
        System.out.println("DELETE PRODUCTS{"+id+"}");
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('product:read')")
    public Product getProductById(@PathVariable("id") Integer id) {
        return PRODUCTS.stream().filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("product " + id + " does not exist"));
    }


}
