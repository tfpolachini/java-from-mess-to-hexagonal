package com.example.productmanager;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        if (product.getId() == null) {
            product.setId(UUID.randomUUID().toString());
            product.setQuantity(0);
            product.setStatus(ProductStatus.ENABLED);
            var count = entityManager.createNativeQuery("INSERT INTO products (id, name, price, quantity, status) VALUES (?,?,?,?,?)")
                    .setParameter(1, product.getId())
                    .setParameter(2, product.getName())
                    .setParameter(3, product.getPrice())
                    .setParameter(4, product.getQuantity())
                    .setParameter(5, product.getStatus().name())
                    .executeUpdate();
            if (count == 0) return ResponseEntity.internalServerError().build();
        } else {
            var count = entityManager.createNativeQuery("UPDATE products SET name = ?, price = ?, quantity = ?, status = ? WHERE id = ?")
                    .setParameter(1, product.getName())
                    .setParameter(2, product.getPrice())
                    .setParameter(3, product.getQuantity())
                    .setParameter(4, product.getStatus().name())
                    .setParameter(5, product.getId())
                    .executeUpdate();
            if (count == 0) return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(getProductById(product.getId()));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        var products = (List<Product>) entityManager.createNativeQuery("SELECT * FROM products", Product.class)
                .getResultList();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id) {
        try {
            return ResponseEntity.ok(getProductById(id));
        } catch (NoResultException nre) {
            return ResponseEntity.notFound().build();
        }
    }

    private Product getProductById(String id) {
        return (Product) entityManager.createNativeQuery("SELECT * FROM products p WHERE p.id = ?", Product.class)
                .setParameter(1, id)
                .getSingleResult();
    }
}
