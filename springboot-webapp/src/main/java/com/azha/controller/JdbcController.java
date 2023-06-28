package com.azha.controller;

import com.azha.Utils.JDBCUtil;
import com.azha.Utils.JedisUtil;
import com.azha.pojo.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.sql.*;

@RestController
@RequestMapping("/jdbc")
public class JdbcController {
    @GetMapping("/test")
    public String test(){
        Connection conn =JDBCUtil.getConnection(false);
        try {
            String sql="select * from product";
            PreparedStatement statement= null;
            statement = conn.prepareStatement(sql);
            ResultSet resultSet=statement.executeQuery();
            while(resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");
                Float price=resultSet.getFloat("price");
                int category=resultSet.getInt("category");
                System.out.println(id+name+price+category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn.toString();
    }

    //查询商品
    @GetMapping("/findById/{id}")
    public String findById(@PathVariable int id) throws SQLException {
        //1.查看缓存
        Product product=getByRedis(id);
        if(product==null){
            System.out.println("缓存中未能找到商品");
            product=getByMysql(id);
            if(product==null){
                System.out.println("数据库中也未能找到商品");
                Product p = new Product();
                p.setId(id);
                saveToRedis(p);
                return "缓存中未能找到商品,数据库中也未能找到商品";
            }else {
                System.out.println("数据库中找到了商品");
                saveToRedis(product);
                System.out.println("存入了redis");
                return "数据库中找到了商品,存入了redis";
            }
        }
        return "缓存中找到了商品";
    }
    //存储商品至Redis hash key field value
    public static void saveToRedis(Product product){
        Jedis jedis= JedisUtil.getJedis();
        String key="product:"+product.getId();
        jedis.hset(key,"name",product.getName()+"");
        jedis.hset(key,"price",product.getPrice()+"");
        jedis.hset(key,"category",product.getCategory()+"");
    }
    //从redis中获取商品
    @GetMapping("/getByRedis/{id}")
    public static Product getByRedis(@PathVariable int id){
        String key="product:"+id;
        Jedis jedis = JedisUtil.getJedis();
        if(jedis.exists(key)){
            String name=jedis.hget(key,"name");
            float price=Float.parseFloat(jedis.hget(key,"price"));
            int category=Integer.parseInt(jedis.hget(key,"category"));
            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setPrice(price);
            product.setCategory(category);
            return product;
        }
        return null;
    }
    //通过MYSQL查询商品
    @GetMapping("/getByMysql/{id}")
    public Product getByMysql(@PathVariable int id) throws SQLException {
        Connection conn = JDBCUtil.getConnection(false);
        String sql = "select * from product where id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String name = resultSet.getString("name");
            Float price = resultSet.getFloat("price");
            int category = resultSet.getInt("category");
            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setPrice(price);
            product.setCategory(category);
            return product;
        }
        return null;
    }
}
