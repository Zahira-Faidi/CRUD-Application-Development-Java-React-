package ma.Hahn.app.dto;

import lombok.Data;
import ma.Hahn.app.enume.ProductCategory;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private ProductCategory category;
    private Long createdById;
}

