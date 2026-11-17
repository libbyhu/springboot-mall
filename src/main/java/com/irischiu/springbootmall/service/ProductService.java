package com.irischiu.springbootmall.service;

import com.irischiu.springbootmall.dto.ProductRequest;
import com.irischiu.springbootmall.model.Product;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}