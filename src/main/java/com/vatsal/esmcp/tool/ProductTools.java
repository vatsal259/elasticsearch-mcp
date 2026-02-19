package com.vatsal.esmcp.tool;

import com.vatsal.esmcp.service.ProductService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class ProductTools {

    private final ProductService productService;

    public ProductTools(ProductService productService) {
        this.productService = productService;
    }

    @Tool(name = "search_products", description = "Search products from Elasticsearch by query text")
    public String searchProducts(
            @ToolParam(description = "Search query", required = true) String query
    ) {
        return String.join("\n", productService.searchProducts(query));
    }
}
