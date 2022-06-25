package com.example.papersubmission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName client
 */
@TableName(value ="client")
@Data
public class Client implements Serializable {
    /**
     * 主键Id（自增）
     */
    @TableId(type = IdType.AUTO)
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}