package com.example.papersubmission.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class ReviewDetailVo {


    //审稿人id
    private Integer reviewerId;

    //审稿人真实姓名
    private String reviewerRealName;

    private String icon;


    //0-接受邀约 1-拒绝邀约
    private Integer status;

    //审稿意见
    private String suggestion;

    //审稿结果 0-通过 1-拒绝
    private Integer conclusion;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
}
