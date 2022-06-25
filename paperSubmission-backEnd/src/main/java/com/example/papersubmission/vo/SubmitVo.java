package com.example.papersubmission.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class SubmitVo {

    /**
     * 投稿人id
     */
    private Integer userId;


    /**
     * submitId
     */
    private Integer submitId;


    /**
     * 论文id
     */
    private String title;


    /**
     * 0-投稿成功，1-退回修改，2-拒收，3-待审稿，4-待分配,5-审稿通过
     */
    private Integer status;



    private String firstAuthor;


    /**
     * 类型名称
     */
    private String type;



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
}
