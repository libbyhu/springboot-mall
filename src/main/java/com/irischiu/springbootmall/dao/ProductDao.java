package com.irischiu.springbootmall.dao;

import com.irischiu.springbootmall.dto.ProductQueryParams;
import com.irischiu.springbootmall.dto.ProductRequest;
import com.irischiu.springbootmall.model.Product;
import java.util.List;

public interface ProductDao {

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
    Integer countProduct(ProductQueryParams productQueryParams);

    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
