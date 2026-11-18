package com.irischiu.springbootmall.service;

import com.irischiu.springbootmall.dto.CreateOrderRequest;
import com.irischiu.springbootmall.dto.OrderQueryParams;
import com.irischiu.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer countOrder(OrderQueryParams orderQueryParams);

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}