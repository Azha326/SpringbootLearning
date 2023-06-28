package com.azha.mapper;

import com.azha.pojo.Order;
import com.azha.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository//声明一个bean
public interface OrderMapper extends BaseMapper<Order> {
    //根据uid搜索订单
    @Select("Select * from t_order where uid=#{uid}")
    List<Order> selectByUid(int uid);

    //查询所有订单同时查询订单的用户
    @Select("select * from t_order")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "name",property = "name"),
            @Result(column = "uid",property = "uid"),
            @Result(column = "uid",property = "user",javaType = User.class,
                    one = @One(select = "com.azha.mapper.UserMapper.selectById"))
    })
    List<Order> selectAllOrdersAndUser();
}
