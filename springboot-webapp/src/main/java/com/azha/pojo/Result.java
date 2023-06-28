package com.azha.pojo;

import com.azha.Utils.ResultCode;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Data
@Repository
public class Result {
    private Boolean success;
    private int code;
    private String message;
    private Map<String,Object> data=new HashMap<>();

    public static Result ok(){
        Result res=new Result();
        res.setSuccess(true);
        res.setCode(ResultCode.SUCCESS);
        res.setMessage("请求成功");
        return res;
    }
    public static Result error(){
        Result res=new Result();
        res.setSuccess(true);
        res.setCode(ResultCode.FAILURE);
        res.setMessage("请求失败");
        return res;
    }
    public Result data(String key,Object value){
        this.data.put(key,value);
        return this;
    }
    public Result data(Map<String,Object> map){
        this.setData(map);
        return this;
    }
}
