package com.vatsal.esmcp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "products")
public class Product {
    @Id
    private String id;
    
    @Field(type = FieldType.Text, name = "name")
    private String name;
    
    @Field(type = FieldType.Keyword, name = "brand")
    private String brand;
    
    @Field(type = FieldType.Double, name = "price")
    private Double price;
    
    @Field(type = FieldType.Keyword, name = "category")
    private String category;
    
    @Field(type = FieldType.Boolean, name = "in_stock")
    private Boolean inStock;
    
    @Field(type = FieldType.Text, name = "description")
    private String description;

    public Product() {}

    public Product(String id, String name, String brand, Double price, String category, Boolean inStock, String description) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.category = category;
        this.inStock = inStock;
        this.description = description;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public Boolean getInStock() { return inStock; }
    public void setInStock(Boolean inStock) { this.inStock = inStock; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
