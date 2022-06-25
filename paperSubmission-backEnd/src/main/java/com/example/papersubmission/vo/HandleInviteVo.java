package com.example.papersubmission.vo;

import lombok.Data;

@Data
public class HandleInviteVo {

    private Integer reviewerInviteId;

//    private Integer submitId;
//
//    private Integer paperId;

    //0-接受邀约 1-拒绝邀约
    private Integer result;

    //审稿意见
    private String suggestion;

    //审稿结果 0-通过 1-拒绝
    private Integer conclusion;
}
