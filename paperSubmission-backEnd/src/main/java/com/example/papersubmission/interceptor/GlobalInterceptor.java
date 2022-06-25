package com.example.papersubmission.interceptor;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.papersubmission.component.HostHolder;
import com.example.papersubmission.exceptions.TokenException;

import com.example.papersubmission.service.ClientService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Data
public class GlobalInterceptor  implements HandlerInterceptor {


    @Value("${jwt.secret-key}")
    private String secret_key;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private ClientService clientService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("X-token");
        if(!StringUtils.isNotEmpty(token)){
            throw new TokenException("token 不能为空");
        }
        if(!JWTUtil.verify(token,secret_key.getBytes())){

            throw new TokenException("token 验证失败，请重新登录！");
        }
        JWT jwt = JWTUtil.parseToken(token);
        Integer userId= (Integer)jwt.getPayload("userId");
        hostHolder.setUser(userId);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }

}
