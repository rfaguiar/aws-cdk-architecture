package br.com.rfaguiar.awsproject01.controller;

import br.com.rfaguiar.awsproject01.model.EventType;
import br.com.rfaguiar.awsproject01.model.HttpResponseError;
import br.com.rfaguiar.awsproject01.model.Product;
import br.com.rfaguiar.awsproject01.repository.ProductRepository;
import br.com.rfaguiar.awsproject01.service.ProductPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductPublisher productPublisher;

    @Autowired
    public ProductController(ProductRepository productRepository, ProductPublisher productPublisher) {
        this.productRepository = productRepository;
        this.productPublisher = productPublisher;
    }

    @GetMapping
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id) {
        return productRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }


    @GetMapping("/bycode")
    public Product findByCode(@RequestParam String code) {
        return productRepository.findByCode(code)
                .orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        Product productCreated = productRepository.save(product);
        productPublisher.publishProductEvent(productCreated, EventType.PRODUCT_CREATED, "user01");
        return productCreated;
    }


    @PutMapping("/{id}")
    public Product updateProduct(@RequestBody Product product, @PathVariable Long id) {
        Product productUpdated = productRepository.findById(id)
                .map(p -> {
                    product.setId(id);
                    return productRepository.save(product);
                }).orElseThrow(EntityNotFoundException::new);

        productPublisher.publishProductEvent(productUpdated, EventType.PRODUCT_UPDATE, "user01");
        return productUpdated;
    }

    @DeleteMapping("/{id}")
    public Product deleteProduct(@PathVariable Long id) {
        Product productDeleted = productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    return product;
                }).orElseThrow(EntityNotFoundException::new);
        productPublisher.publishProductEvent(productDeleted, EventType.PRODUCT_DELETED, "user01");
        return productDeleted;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HttpResponseError EntityNotFoundExceptionHandler() {
        return new HttpResponseError(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
    }

}
