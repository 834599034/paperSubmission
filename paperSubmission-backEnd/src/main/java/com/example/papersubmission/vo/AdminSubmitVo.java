package com.example.papersubmission.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.example.papersubmission.entity.Client;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AdminSubmitVo {
    /**
     * 投稿人id
     */
    private Integer userId;


    /**
     * submitId
      */
    private Integer submitId;


    /**
     * 论文题目
     */
    private String title;


    /**
     * 0-投稿成功，1-退回修改，2-拒收，3-待审稿，4-待分配,5-审稿通过
     */
    private Integer status;

    /**
     * 第一作者名称
     */

    private String firstAuthor;

    /**
     * 审稿人信息
     */
    private List<Client> reviewers;


    /**
     * 类型名称
     */
    private String type;


    private Integer inviteNum;


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
