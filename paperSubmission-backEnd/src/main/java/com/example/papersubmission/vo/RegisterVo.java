package com.example.papersubmission.vo;

import lombok.Data;

@Data
public class RegisterVo {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 真实姓名
     */

    private String realName;

}
