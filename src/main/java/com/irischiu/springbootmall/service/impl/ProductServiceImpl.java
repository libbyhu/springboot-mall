package com.irischiu.springbootmall.service.impl;

import com.irischiu.springbootmall.dao.ProductDao;
import com.irischiu.springbootmall.model.Product;
import com.irischiu.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}