package com.example.papersubmission.vo;

import lombok.Data;

@Data
public class AsignedReviewerVo {

    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 头像
     */
    private String icon;

    /**
     * 0-3 0-普通用户，1-审稿人，2-超级管理员
     */
    private Integer role;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 真实姓名
     */
    private String realName;


    private Integer inviteStatus;

}
