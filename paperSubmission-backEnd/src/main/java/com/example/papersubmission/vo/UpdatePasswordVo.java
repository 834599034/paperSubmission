package com.example.papersubmission.vo;

import lombok.Data;

@Data
public class UpdatePasswordVo {


    /**
     * 原密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;
}
