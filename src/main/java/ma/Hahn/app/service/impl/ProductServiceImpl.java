package ma.Hahn.app.service.impl;

import lombok.RequiredArgsConstructor;
import ma.Hahn.app.dto.ProductDTO;
import ma.Hahn.app.entity.Product;
import ma.Hahn.app.entity.User;
import ma.Hahn.app.exception.ResourceNotFoundException;
import ma.Hahn.app.mapper.ProductMapper;
import ma.Hahn.app.repository.ProductRepository;
import ma.Hahn.app.repository.UserRepository;
import ma.Hahn.app.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductMapper mapper;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id).map(mapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public ProductDTO createProduct(ProductDTO dto) {
        User user = userRepository.findById(dto.getCreatedById()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Product product = mapper.toEntity(dto, user);
        return mapper.toDto(productRepository.save(product));
    }

    public ProductDTO updateProduct(Long id, ProductDTO dto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        return mapper.toDto(productRepository.save(product));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
