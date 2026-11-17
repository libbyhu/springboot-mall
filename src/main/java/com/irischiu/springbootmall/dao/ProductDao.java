package com.irischiu.springbootmall.dao;

import com.irischiu.springbootmall.dto.ProductRequest;
import com.irischiu.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
}