package com.example.papersubmission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName resource
 */
@TableName(value ="resource")
@Data
public class Resource implements Serializable {
    /**
     * 主键自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 资源地址
     */
    private String resourceUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}