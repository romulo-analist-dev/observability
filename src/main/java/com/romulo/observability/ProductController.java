package com.romulo.observability;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        log.info("Requisição para criar produto recebida: {}", product);

        Product savedProduct = productRepository.save(product);

        log.info("Produto criado com sucesso com ID: {}", savedProduct.getId());
        return savedProduct;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        log.info("Requisição para listar todos os produtos.");

        List<Product> products = productRepository.findAll();

        log.info("{} produtos encontrados.", products.size());
        return products;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        log.info("Requisição para buscar produto com ID: {}", id);

        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {

            log.info("Produto com ID: {} encontrado.", id);
            return ResponseEntity.ok(product.get());
        } else {

            log.warn("Produto com ID: {} não encontrado.", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        log.info("Requisição para atualizar produto com ID: {}. Novos dados: {}", id, productDetails);
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productDetails.getName());
            product.setPrice(productDetails.getPrice());

            final Product updatedProduct = productRepository.save(product);

            log.info("Produto com ID: {} atualizado com sucesso.", id);
            return ResponseEntity.ok(updatedProduct);
        } else {

            log.warn("Tentativa de atualização falhou. Produto com ID: {} não encontrado.", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.info("Requisição para deletar produto com ID: {}", id);

        if (productRepository.existsById(id)) {

            productRepository.deleteById(id);

            log.info("Produto com ID: {} deletado com sucesso.", id);
            return ResponseEntity.ok().build();
        } else {

            log.error("Tentativa de deleção falhou. Produto com ID: {} não encontrado.", id);
            return ResponseEntity.notFound().build();
        }
    }
}
