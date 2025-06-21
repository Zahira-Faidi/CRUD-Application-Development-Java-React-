package ma.Hahn.app.mapper;

import ma.Hahn.app.dto.ProductDTO;
import ma.Hahn.app.entity.Product;
import ma.Hahn.app.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class ProductMapper {

    public Product toEntity(ProductDTO dto, User user) {
        Product p = new Product();
        p.setId(dto.getId());
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice());
        p.setCategory(dto.getCategory());
        p.setCreatedAt(LocalDateTime.now());
        p.setCreatedBy(user);
        return p;
    }

    public ProductDTO toDto(Product entity) {
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setCategory(entity.getCategory());
        dto.setCreatedById(entity.getCreatedBy().getId());
        return dto;
    }
}
