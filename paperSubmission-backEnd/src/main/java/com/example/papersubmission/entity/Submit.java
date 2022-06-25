package com.example.papersubmission.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName submit
 */
@TableName(value ="submit")
@Data
public class Submit implements Serializable {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 投稿人id
     */
    private Integer userId;

    /**
     * 论文id
     */
    private Integer paperId;



    /**
     * 新建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 0-投稿成功，1-退回修改，2-拒收，3-待审稿，4-待分配
     */
    private Integer status;

    /**
     * 审稿意见
     */
    private String suggestion;

    /**
     * 类型id
     */
    private Integer typeId;


    /**
     * 总邀请人数
     */
    private Integer totalInvite;


    /**
     * 已同意邀请人数
     */
    private Integer acceptInvite;

    /**
     * 剩余邀请人数
     */
    private Integer inviteNum;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}