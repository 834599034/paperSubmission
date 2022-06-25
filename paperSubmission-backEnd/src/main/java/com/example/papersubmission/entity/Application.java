package com.example.papersubmission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName application
 */
@TableName(value ="application")
@Data
public class Application implements Serializable {
    /**
     * 主键id自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 审稿人id
     */
    private Integer reviewerId;

    /**
     * 论文id
     */
    private Integer paperId;

    /**
     * 0-待审批，1-批准，2-拒绝
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}