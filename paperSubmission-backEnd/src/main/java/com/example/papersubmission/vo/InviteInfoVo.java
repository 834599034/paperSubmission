package com.example.papersubmission.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class InviteInfoVo {


    private Integer reviewerInviteId;

    private Integer submitId;
    /**
     * 0-同意审稿 1-拒绝审稿 2-审稿完成 3-待接受
     *
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
     * 论文题目
     */
    private String title;

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
}
