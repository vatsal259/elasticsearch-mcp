package com.vatsal.esmcp.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.vatsal.esmcp.model.Product;
import com.vatsal.esmcp.repository.ProductRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ElasticsearchClient elasticsearchClient;

    public ProductService(ProductRepository productRepository, ElasticsearchClient elasticsearchClient) {
        this.productRepository = productRepository;
        this.elasticsearchClient = elasticsearchClient;
    }

    @Tool(description = "Get all products from the catalog")
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    @Tool(description = "Get a specific product by its ID")
    public Product getProductById(
            @Schema(description = "Product ID") String id) {
        return productRepository.findById(id).orElse(null);
    }

    @Tool(description = "Search products by name")
    public List<Product> searchByName(
            @Schema(description = "Product name to search") String name) {
        return productRepository.findByName(name);
    }

    @Tool(description = "Search products by category")
    public List<Product> searchByCategory(
            @Schema(description = "Category name") String category) {
        return productRepository.findByCategory(category);
    }

    @Tool(description = "Search products by brand")
    public List<Product> searchByBrand(
            @Schema(description = "Brand name") String brand) {
        return productRepository.findByBrand(brand);
    }

    @Tool(description = "Get all in-stock products")
    public List<Product> searchInStockProducts() {
        return productRepository.findByInStock(true);
    }

    @Tool(description = "Search products within a price range")
    public List<Product> searchByPriceRange(
            @Schema(description = "Minimum price") Double minPrice,
            @Schema(description = "Maximum price") Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Tool(description = "Full-text search in product descriptions")
    public List<Product> fullTextSearch(
            @Schema(description = "Search text") String searchText) throws IOException {
        SearchResponse<Product> response = elasticsearchClient.search(s -> s
                        .index("products")
                        .query(q -> q.match(m -> m.field("description").query(searchText))),
                Product.class
        );
        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    @Tool(description = "Advanced search with category, price, and stock filters")
    public List<Product> advancedSearch(
            @Schema(description = "Product category") String category,
            @Schema(description = "Maximum price") Double maxPrice,
            @Schema(description = "Stock availability") Boolean inStock) throws IOException {
        SearchResponse<Product> response = elasticsearchClient.search(s -> s
                        .index("products")
                        .query(q -> q.bool(b -> b
                                .must(m -> m.term(t -> t.field("category").value(category)))
                                .must(m -> m.term(t -> t.field("in_stock").value(inStock)))
                                .filter(f -> f.range(r -> r.field("price").lte(JsonData.of(maxPrice))))
                        )),
                Product.class
        );
        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    @Tool(description = "Create a new product in the catalog")
    public Product createProduct(
            @Schema(description = "Product details") Product product) {
        return productRepository.save(product);
    }

    @Tool(description = "Update the price of a product")
    public Product updateProductPrice(
            @Schema(description = "Product ID") String id,
            @Schema(description = "New price") Double newPrice) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setPrice(newPrice);
            return productRepository.save(product);
        }
        throw new RuntimeException("Product not found with id: " + id);
    }

    @Tool(description = "Update the stock status of a product")
    public Product updateProductStock(
            @Schema(description = "Product ID") String id,
            @Schema(description = "Stock availability") Boolean inStock) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setInStock(inStock);
            return productRepository.save(product);
        }
        throw new RuntimeException("Product not found with id: " + id);
    }

    @Tool(description = "Delete a product from the catalog")
    public void deleteProduct(
            @Schema(description = "Product ID") String id) {
        productRepository.deleteById(id);
    }

    @Tool(description = "Count total number of products")
    public long countProducts() {
        return productRepository.count();
    }
}
