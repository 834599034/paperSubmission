package com.example.papersubmission.vo;

import lombok.Data;

import java.util.List;

@Data
public class AssignVo {

    /**
     * 剩余邀请人数
     */
    private Integer inviteNum;

    /**
     * 已分配审稿人消息
     */
    private List<AsignedReviewerVo> reviewerVos;


}
