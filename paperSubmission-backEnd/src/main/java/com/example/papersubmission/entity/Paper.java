package com.example.papersubmission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName paper
 */
@TableName(value ="paper")
@Data
public class Paper implements Serializable {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 论文题目
     */
    private String title;

    /**
     * 第一作者id
     */
    private Integer firstAuthor;

    /**
     * 其他作者名称
     */
    private String otherAuthor;

    /**
     * 关键字
     */
    private String primaryKey;

    /**
     * 摘要
     */
    private String abstractContext;

    /**
     * pdf地址
     */
    private String paperUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}