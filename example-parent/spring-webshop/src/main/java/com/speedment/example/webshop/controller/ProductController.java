package com.speedment.example.webshop.controller;

import com.speedment.example.webshop.db.products.Product;
import com.speedment.example.webshop.db.products.ProductImpl;
import com.speedment.example.webshop.db.products.ProductManager;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.component.transaction.TransactionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@CrossOrigin
@RestController
@RequestMapping("products")
public class ProductController {

    private @Autowired
    ProductManager products;
    private @Autowired TransactionComponent transactions;

    @GetMapping
    List<Product> getProducts(
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="pageSize", defaultValue="10") int pageSize) {

        return products.stream()
            .skip(page * pageSize)
            .limit(pageSize)
            .collect(toList());
    }

    @GetMapping("/{id}")
    Product getProductById(@PathVariable("id") int id) {
        return products.stream()
            .filter(Product.ID.equal(id))
            .findAny().orElseThrow();
    }

    private final static class PostProduct {
        private @NotNull @NotEmpty String name;
        private @NotNull String description;
        private @Min(1) int price;
        private Integer stock;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public Integer getStock() {
            return stock;
        }

        public void setStock(Integer stock) {
            this.stock = stock;
        }
    }

    @PostMapping
    void postProduct(@RequestBody PostProduct body) {
        products.persist(new ProductImpl()
            .setId(ThreadLocalRandom.current().nextInt())
            .setName(body.getName())
            .setDescription(body.getDescription())
            .setPrice(body.getPrice())
            .setStock(ofNullable(body.getStock()).orElse(-1))
        );
    }

    private final static class PutProduct {
        private String name;
        private String description;
        private Integer price;
        private Integer stock;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Integer getStock() {
            return stock;
        }

        public void setStock(Integer stock) {
            this.stock = stock;
        }
    }

    @PutMapping("/{id}")
    void putProduct(@PathVariable("id") int id, @RequestBody PutProduct body) {
        final Product product = new ProductImpl().setId(id);
        ofNullable(body.getName()).ifPresent(product::setName);
        ofNullable(body.getDescription()).ifPresent(product::setDescription);
        ofNullable(body.getPrice()).ifPresent(product::setPrice);
        ofNullable(body.getStock()).ifPresent(product::setStock);
        products.update(product);
    }

    @DeleteMapping("/{id}")
    void deleteProduct(@PathVariable("id") int id) {
        products.remove(new ProductImpl().setId(id));
    }

    @PostMapping("/{id}/buy")
    ResponseEntity<Void> buyProduct(@PathVariable("id") int id) {
        final TransactionHandler txHandler = transactions.createTransactionHandler();
        return txHandler.createAndApply(tx -> {
            try {
                final Product product = getProductById(id);
                if (product.getStock() > 0) {
                    product.setStock(product.getStock() - 1);
                    products.update(product);
                    return ResponseEntity.noContent().build();
                } else {
                    return ResponseEntity.badRequest().build();
                }
            } finally {
                tx.commit();
            }
        });
    }
}