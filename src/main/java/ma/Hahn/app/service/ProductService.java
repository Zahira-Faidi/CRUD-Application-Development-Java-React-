package ma.Hahn.app.service;

import ma.Hahn.app.dto.ProductDTO;
import ma.Hahn.app.entity.User;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
    ProductDTO createProduct(ProductDTO dto);
    ProductDTO updateProduct(Long id, ProductDTO dto);
    void deleteProduct(Long id);
    List<ProductDTO> findByCreatedBy(User user);
}