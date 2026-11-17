package com.irischiu.springbootmall.dao;

import com.irischiu.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);

}