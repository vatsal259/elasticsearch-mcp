package com.vatsal.esmcp.initializer;

import com.vatsal.esmcp.model.Product;
import com.vatsal.esmcp.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    private final ProductRepository productRepository;

    public DataInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        System.out.println("========================================");
        System.out.println("Checking if sample data needs to be loaded...");

        long count = productRepository.count();
        if (count > 0) {
            System.out.println("Found " + count + " existing products. Skipping initialization.");
            System.out.println("========================================");
            return;
        }

        System.out.println("No existing data found. Loading sample products...");

        List<Product> sampleProducts = Arrays.asList(
                new Product("1", "Laptop", "Dell", 899.99, "Electronics", true, "High-performance laptop with 16GB RAM and 512GB SSD"),
                new Product("2", "Smartphone", "Samsung", 699.99, "Electronics", true, "Latest model with excellent camera and 5G support"),
                new Product("3", "Headphones", "Sony", 149.99, "Audio", false, "Noise-canceling wireless headphones with premium sound"),
                new Product("4", "Monitor", "LG", 299.99, "Electronics", true, "27-inch 4K display with HDR support"),
                new Product("5", "Keyboard", "Logitech", 79.99, "Accessories", true, "Mechanical gaming keyboard with RGB lighting"),
                new Product("6", "Mouse", "Razer", 59.99, "Accessories", true, "Gaming mouse with high DPI and programmable buttons"),
                new Product("7", "Tablet", "Apple", 499.99, "Electronics", true, "iPad with retina display and Apple Pencil support"),
                new Product("8", "Webcam", "Logitech", 89.99, "Accessories", false, "HD webcam for video calls and streaming"),
                new Product("9", "Speaker", "Bose", 199.99, "Audio", true, "Bluetooth portable speaker with deep bass"),
                new Product("10", "Router", "TP-Link", 129.99, "Networking", true, "WiFi 6 dual-band wireless router")
        );

        productRepository.saveAll(sampleProducts);
        
        System.out.println("✅ Successfully loaded " + sampleProducts.size() + " sample products!");
        System.out.println("========================================");
    }
}
