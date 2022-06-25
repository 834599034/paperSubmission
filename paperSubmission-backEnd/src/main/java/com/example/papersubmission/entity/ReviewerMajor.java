package com.example.papersubmission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName reviewer_major
 */
@TableName(value ="reviewer_major")
@Data
public class ReviewerMajor implements Serializable {
    /**
     * 主键ID自增
     */
    @TableId
    private Integer id;

    /**
     * 审稿人id
     */
    private Integer reviewerId;

    /**
     * 审稿人专业方向
     */
    private Integer majorId;


    /**
     * 专业名
     */
    private String major;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}