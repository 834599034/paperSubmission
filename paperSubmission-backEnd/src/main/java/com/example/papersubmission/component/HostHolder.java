package com.example.papersubmission.component;

import com.example.papersubmission.entity.Client;
import org.springframework.stereotype.Component;

/**
 *持有用户信息，用于代替session对象
 */
@Component
public class HostHolder {

    //ThreadLocal本质是以线程为key存储元素
    private ThreadLocal<Integer> users = new ThreadLocal<>();

    public void setUser(Integer user){
        users.set(user);
    }

    public Integer getUser(){
        return users.get();
    }

    public void clear(){
        users.remove();
    }
}
