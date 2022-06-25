package com.example.papersubmission.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.papersubmission.entity.Client;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.papersubmission.exceptions.EmailException;
import com.example.papersubmission.exceptions.PhoneException;
import com.example.papersubmission.exceptions.UsernameException;
import com.example.papersubmission.vo.ClientVo;
import com.example.papersubmission.vo.LoginVo;
import com.example.papersubmission.vo.RegisterVo;
import com.example.papersubmission.vo.UpdatePasswordVo;

import java.util.List;
import java.util.Map;

/**
* @author h8345
* @description 针对表【client】的数据库操作Service
* @createDate 2022-05-21 20:55:59
*/
public interface ClientService extends IService<Client> {
    IPage<Client> queryPage(Map<String,Object> params);

    Integer checkLogin(LoginVo loginVo);

    void register(RegisterVo registerVo);

    /**
     * 判断邮箱是否重复
     * @param phone
     * @return
     */
    void checkPhoneUnique(String phone) throws PhoneException;

    /**
     * 判断用户名是否重复
     * @param userName
     * @return
     */
    void checkUserNameUnique(String userName) throws UsernameException;


    /**
     * 判断邮箱是否重复
     * @param email
     * @return
     */
    void checkEmailUnique(String email) throws EmailException;

    Boolean updatePassword(UpdatePasswordVo updatePasswordVo,Integer userId);

    List<Client> searchAuthor(String key);

    ClientVo getClientInfo(Integer clientId);

    void updateClientInfo(ClientVo clientVo, Integer userId);

    List<ClientVo> getAllReviewers();
}
