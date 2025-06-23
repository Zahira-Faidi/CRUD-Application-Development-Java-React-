package ma.Hahn.app.service;

import ma.Hahn.app.dto.ProductDTO;
import ma.Hahn.app.entity.Product;
import ma.Hahn.app.entity.User;
import ma.Hahn.app.enums.ProductCategory;
import ma.Hahn.app.mapper.ProductMapper;
import ma.Hahn.app.repository.ProductRepository;
import ma.Hahn.app.repository.UserRepository;
import ma.Hahn.app.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void shouldReturnAllProducts() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");

        Product product = new Product();
        product.setId(1L);
        product.setName("Phone");
        product.setDescription("Android phone");
        product.setPrice(300.0);
        product.setCategory(ProductCategory.ELECTRONICS);
        product.setCreatedBy(user);

        ProductDTO dto = new ProductDTO();
        dto.setId(1L);
        dto.setName("Phone");
        dto.setDescription("Android phone");
        dto.setPrice(300.0);
        dto.setCategory(ProductCategory.ELECTRONICS);
        dto.setCreatedById(1L);

        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productMapper.toDto(product)).thenReturn(dto);

        List<ProductDTO> result = productService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals("Phone", result.get(0).getName());
    }

    @Test
    void shouldReturnProductsByUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");

        Product product = new Product();
        product.setId(2L);
        product.setName("TV");
        product.setDescription("Smart TV");
        product.setPrice(1000.0);
        product.setCategory(ProductCategory.ELECTRONICS);
        product.setCreatedBy(user);

        ProductDTO dto = new ProductDTO();
        dto.setId(2L);
        dto.setName("TV");
        dto.setDescription("Smart TV");
        dto.setPrice(1000.0);
        dto.setCategory(ProductCategory.ELECTRONICS);
        dto.setCreatedById(1L);

        when(productRepository.findByCreatedBy(user)).thenReturn(List.of(product));
        when(productMapper.toDto(product)).thenReturn(dto);

        List<ProductDTO> result = productService.findByCreatedBy(user);

        assertEquals(1, result.size());
        assertEquals("TV", result.get(0).getName());
    }

    @Test
    void shouldReturnProductById() {
        Product product = new Product();
        product.setId(3L);
        product.setName("Laptop");
        product.setDescription("Gaming Laptop");
        product.setPrice(2000.0);
        product.setCategory(ProductCategory.ELECTRONICS);

        ProductDTO dto = new ProductDTO();
        dto.setId(3L);
        dto.setName("Laptop");
        dto.setDescription("Gaming Laptop");
        dto.setPrice(2000.0);
        dto.setCategory(ProductCategory.ELECTRONICS);

        when(productRepository.findById(3L)).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(dto);

        ProductDTO result = productService.getProductById(3L);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
    }

    @Test
    void shouldDeleteProduct() {
        assertDoesNotThrow(() -> productService.deleteProduct(4L));
        verify(productRepository, times(1)).deleteById(4L);
    }


    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        when(productRepository.findById(5L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> productService.getProductById(5L));
        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    void shouldCreateProduct() {
        ProductDTO dto = new ProductDTO();
        dto.setName("Tablet");
        dto.setDescription("Android Tablet");
        dto.setPrice(400.0);
        dto.setCategory(ProductCategory.ELECTRONICS);
        dto.setCreatedById(1L);

        User user = new User();
        user.setId(1L);

        Product product = new Product();
        product.setName("Tablet");

        Product savedProduct = new Product();
        savedProduct.setId(6L);
        savedProduct.setName("Tablet");

        ProductDTO savedDto = new ProductDTO();
        savedDto.setId(6L);
        savedDto.setName("Tablet");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productMapper.toEntity(dto, user)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(savedProduct);
        when(productMapper.toDto(savedProduct)).thenReturn(savedDto);

        ProductDTO result = productService.createProduct(dto);

        assertNotNull(result);
        assertEquals(6L, result.getId());
        assertEquals("Tablet", result.getName());
    }

    @Test
    void shouldUpdateProduct() {
        ProductDTO dto = new ProductDTO();
        dto.setId(7L);
        dto.setName("Updated Phone");
        dto.setDescription("Updated description");
        dto.setPrice(350.0);
        dto.setCategory(ProductCategory.ELECTRONICS);

        Product existingProduct = new Product();
        existingProduct.setId(7L);
        existingProduct.setName("Old Phone");

        Product updatedProduct = new Product();
        updatedProduct.setId(7L);
        updatedProduct.setName("Updated Phone");

        ProductDTO updatedDto = new ProductDTO();
        updatedDto.setId(7L);
        updatedDto.setName("Updated Phone");

        when(productRepository.findById(7L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(updatedProduct);
        when(productMapper.toDto(updatedProduct)).thenReturn(updatedDto);

        ProductDTO result = productService.updateProduct(7L, dto);

        assertNotNull(result);
        assertEquals("Updated Phone", result.getName());
    }
}
