package com.example.papersubmission.vo;

import lombok.Data;

import java.util.List;

@Data
public class SubmitDetailVo {


    private Integer submitId;


    /**
     * Submit状态
     * 0-投稿成功，1-退回修改，2-拒收，3-待审稿，4-待分配
     */
    private Integer status;

    /**
     * 审稿意见
     */
    private String suggestion;


    private List<ReviewDetailVo> reviewDetailVos;
}
