package ma.Hahn.app.repository;

import ma.Hahn.app.dto.ProductDTO;
import ma.Hahn.app.entity.Product;
import ma.Hahn.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCreatedBy(User user);
}
