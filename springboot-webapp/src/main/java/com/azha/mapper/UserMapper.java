package com.azha.mapper;

import com.azha.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository//声明一个bean
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from t_user")
     List<User> selectUser();

    @Select("select * from t_user where id = #{id}")
     User selectById(int id);

    @Insert("Insert into t_user (name, age) value(#{name},#{age})")
     int insertUser(User user);

    //查询所有用户以及订单
    @Select("select * from t_user")
    @Results(//结果集映射，可以传入多个@Result
            {
                    @Result(column = "id",property = "id"),//每个Result都是一个参数，可以对每个字段进行赋值，
                    @Result(column = "name",property = "username"),//前面是表里的字段，后面是类里的字段
                    @Result(column = "age",property = "age"),
                    @Result(column = "id",property = "orders",javaType = List.class,
                        many = @Many(select = "com.azha.mapper.OrderMapper.selectByUid"))
            }
    )
    List<User> selectAllUserAndOrders();
}
