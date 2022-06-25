package com.example.papersubmission.controller;

import cn.hutool.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.papersubmission.component.HostHolder;
import com.example.papersubmission.constant.BizCodeEnum;
import com.example.papersubmission.entity.Client;
import com.example.papersubmission.service.ClientService;
import com.example.papersubmission.utils.CommonResult;
import com.example.papersubmission.vo.ClientVo;
import com.example.papersubmission.vo.LoginVo;
import com.example.papersubmission.vo.RegisterVo;
import com.example.papersubmission.vo.UpdatePasswordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("papersubmission/client")
public class ClientController {

    @Value("${jwt.secret-key}")
    private String secret_key;

    @Autowired
    private ClientService clientService;

    @Autowired
    private HostHolder hostHolder;

    @PostMapping("/register")
    public CommonResult register(@RequestBody RegisterVo registerVo){
        clientService.register(registerVo);
        return CommonResult.success();
    }

//    @PostMapping("/test")
//    public void test(){
//        new Message().sendMessage("李魁昊真帅");
//    }

    @PostMapping("/login")
    public CommonResult login(@RequestBody LoginVo loginVo){
        if(clientService.checkLogin(loginVo)!=-1){
//            登陆成功，生成JWT
            String token = JWT.create()
                    .setPayload("userId",clientService.checkLogin(loginVo))
                    .setKey(secret_key.getBytes())
                    .sign();
            HashMap<String,Object> data= new HashMap<>();
            data.put("token",token);

            RedisTemplate redisTemplate=new RedisTemplate();
            return CommonResult.success(data);
        }else {
            return CommonResult.error(400,"用户名密码错误");
        }
    }

    @GetMapping("/clientInfo")
    public CommonResult getClientInfo(){
        Integer clientId = hostHolder.getUser();
        ClientVo clientvo=clientService.getClientInfo(clientId);
//        Client client = clientService.getById(clientId);
        if(clientvo==null){
            return CommonResult.error(400,"获取用户信息失败");
        }
        return CommonResult.success(clientvo);
    }

    @PostMapping("/updatePassword")
    public CommonResult updatePassword(@RequestBody UpdatePasswordVo updatePasswordVo){
        Integer user = hostHolder.getUser();
        Boolean updatePassword = clientService.updatePassword(updatePasswordVo,user);
        if(updatePassword){
            return CommonResult.success();
        }else {
            return CommonResult.error(BizCodeEnum.UPDATE_PASSWORD_ERROR.getCode(), BizCodeEnum.UPDATE_PASSWORD_ERROR.getMessage());
        }
    }

    @PostMapping("/updateInfo")
    public CommonResult updateInfo(@RequestBody Client client){
        clientService.update(client,new QueryWrapper<Client>().eq("user_name",client.getUserName()));
        return CommonResult.success();
    }


    @GetMapping("/searchAuthor")
    public CommonResult searchAuthor(@RequestParam("key") String key){
        List<Client> clientList=clientService.searchAuthor(key);
        return CommonResult.success(clientList);
    }


    @PostMapping("/updateClientInfo")
    public CommonResult updateClientInfo(@RequestBody ClientVo clientVo){
        Integer userId = hostHolder.getUser();
        clientService.updateClientInfo(clientVo,userId);
        return CommonResult.success();

    }

    /**
     * 获取审稿人列表
     */
    @GetMapping("/getAllReviewers")
    public CommonResult getAllReviewers(){
        List<ClientVo> clientVos=clientService.getAllReviewers();
        return CommonResult.success(clientVos);
    }




}
