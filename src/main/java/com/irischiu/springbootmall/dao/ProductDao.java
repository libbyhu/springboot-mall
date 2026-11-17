package com.irischiu.springbootmall.dao;

import com.irischiu.springbootmall.dto.ProductRequest;
import com.irischiu.springbootmall.model.Product;
import com.irischiu.springbootmall.constant.ProductCategory;
import java.util.List;

public interface ProductDao {

    List<Product> getProducts(ProductCategory category, String search);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
