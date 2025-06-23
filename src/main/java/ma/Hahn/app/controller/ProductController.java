package ma.Hahn.app.controller;


import lombok.RequiredArgsConstructor;
import ma.Hahn.app.dto.ProductDTO;
import ma.Hahn.app.entity.User;
import ma.Hahn.app.service.ProductService;
import ma.Hahn.app.service.impl.UserServiceImpl;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final UserServiceImpl userService;
    @GetMapping
    public List<ProductDTO> getAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO getById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO dto) {
        return new ResponseEntity<>(productService.createProduct(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable Long id, @RequestBody ProductDTO dto) {
        return productService.updateProduct(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductDTO>> getProductsByUser(@PathVariable Long userId) {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(productService.findByCreatedBy(user));
    }
}