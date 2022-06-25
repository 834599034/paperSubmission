package com.example.papersubmission.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class ReviewedPaperVo {
    /**
     * 论文id
     */
    private Integer paperId;

    /**
     * 论文题目
     */

    private String title;


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
}
