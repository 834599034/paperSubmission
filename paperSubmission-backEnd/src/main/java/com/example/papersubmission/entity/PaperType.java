package com.example.papersubmission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName paper_type
 */
@TableName(value ="paper_type")
@Data
public class PaperType implements Serializable {
    /**
     * 自增主键
     */
    @TableId
    private Integer id;

    /**
     * 论文类别
     */
    private String typeName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}