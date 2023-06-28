package com.azha.controller;

import com.azha.pojo.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    @GetMapping("/helloget")//等价于@RequestMapping(value="/hello",method=RequestMethod.GET)
    @ResponseBody
    public String helloGet(String name,int age){
        return "hello world "+name+" "+age;
    }

    @PostMapping("/hellopost")
    public String helloPost(User user){
        System.out.println(user);
        return "hello world "+user.getUsername()+" "+user.getAge();
    }

    @PostMapping("/hellopostjson")
    public String helloPostJson(@RequestBody User user){
        System.out.println(user);
        return "hello world "+user.getUsername()+" "+user.getAge();
    }

    @GetMapping("/tpf/**")
    public String tpf(){
        return "通配符请求（用的少）";
    }
}
