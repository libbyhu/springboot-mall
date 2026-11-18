package com.irischiu.springbootmall.dao;

import com.irischiu.springbootmall.model.Order;
import com.irischiu.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}