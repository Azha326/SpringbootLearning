package com.azha.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;
@TableName("t_user")
@Data
public class User {
    @TableId(value = "id", type = IdType.AUTO)//声明自增ID
    private int id;
    private String username;
    private String password;
    private int age;

    @TableField(exist = false)//声明该字段并不存在于数据表，而是链表查询需要的映射
    private List<Order> orders;

}
