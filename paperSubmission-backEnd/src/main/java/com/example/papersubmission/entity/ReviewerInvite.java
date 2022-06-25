package com.example.papersubmission.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 
 * @TableName reviewer_invite
 */
@TableName(value ="reviewer_invite")
@Data
public class ReviewerInvite implements Serializable {
    /**
     * 主键自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 投稿 id
     */
    private Integer submitId;

    /**
     * 论文id
     */
    private Integer paperId;

    /**
     * 审稿人id
     */
    private Integer reviewerId;

    /**
     * 0-同意审稿 1-拒绝审稿 2-审稿完成 3-待接受
     */
    private Integer status;

    /**
     * 意见
     */
    private String suggestion;

    /**
     * 结果
     */
    private Integer conclusion;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}