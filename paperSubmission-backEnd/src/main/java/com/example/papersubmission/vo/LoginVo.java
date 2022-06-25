package com.example.papersubmission.vo;

import lombok.Data;

@Data
public class LoginVo {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 0-3 0-普通用户，1-审稿人，2-超级管理员
     */
    private Integer role;
}
