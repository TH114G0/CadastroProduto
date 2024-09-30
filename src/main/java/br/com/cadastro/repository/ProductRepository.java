package br.com.cadastro.repository;

import br.com.cadastro.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface  ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p.name, p.product_value FROM Product p ORDER BY p.product_value DESC")
    List<Object[]> findAllProductsOrderedByValue();
}
