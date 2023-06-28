package com.azha.controller;

import com.azha.Utils.JwtUtils;
import com.azha.mapper.UserMapper;
import com.azha.pojo.Result;
import com.azha.pojo.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired//Spring的自动注入
    private UserMapper userMapper;

    @ApiOperation("关于用户信息的crud")
    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable int id){
        System.out.println(id);
        return "根据id获取用户信息";
    }

    @GetMapping("/user")
    public String select(){
        List<User> list=userMapper.selectUser();
        List<User> list1=userMapper.selectList(null);//括号里是传条件的
        System.out.println(list);
        System.out.println(list1);
        return list.toString();
    }
    @PostMapping("/insertUser")
    public String add(User user){
        int i= userMapper.insertUser(user);
//        System.out.println(list);
        if(i>0){
            return "插入成功";
        }
        return "插入失败";
    }
//获取所有用户和订单
    @GetMapping("/findAll")
    public Result selectAllUserAndOrders(){
        return Result.ok().data("data",userMapper.selectAllUserAndOrders());
    }

    //分页查询
    @GetMapping("/findByPage")
    public IPage findByPage(){
        Page<User> page=new Page<>(0,2);
        IPage iPage=userMapper.selectPage(page,null);
        return iPage;
    }

    //获取token
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        String token = JwtUtils.generateToken(user.getUsername());
        System.out.println(user.getUsername());
        return Result.ok().data("token",token);
    }

    //获取token
    @GetMapping("/info")
    public Result info(String token){
        String name=JwtUtils.getClaimsByToken(token).getSubject();
        String url="https://img1.baidu.com/it/u=303482206,2724856365&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500";
        return Result.ok().data("name",name).data("avatar",url);
    }

    @PostMapping("/logout")
    public Result logout(){
        return Result.ok();
    }

}
