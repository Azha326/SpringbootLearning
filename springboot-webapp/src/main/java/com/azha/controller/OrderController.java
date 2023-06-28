package com.azha.controller;

import com.azha.mapper.OrderMapper;
import com.azha.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderMapper orderMapper;

    @GetMapping("/order/findAll")
    public List<Order> findAll(){
        List<Order> list =orderMapper.selectAllOrdersAndUser();
        return list;
    }
}
