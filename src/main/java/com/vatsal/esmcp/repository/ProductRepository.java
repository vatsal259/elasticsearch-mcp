package com.vatsal.esmcp.repository;

import com.vatsal.esmcp.model.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, String> {
    List<Product> findByName(String name);
    List<Product> findByCategory(String category);
    List<Product> findByBrand(String brand);
    List<Product> findByInStock(Boolean inStock);
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    List<Product> findByCategoryAndInStock(String category, Boolean inStock);
    List<Product> findByNameContaining(String keyword);
    List<Product> findByCategoryAndPriceLessThanEqual(String category, Double maxPrice);
}
