package com.irischiu.springbootmall.service;

import com.irischiu.springbootmall.dto.CreateOrderRequest;
import com.irischiu.springbootmall.model.Order;

public interface OrderService {

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}