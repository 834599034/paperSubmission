package com.example.papersubmission.constant;

public enum StatusCodeEnum {
    //邀约状态
    ACCEPT_INVITE(0),
    REJECT_INVITE(1),
    FINISH_REVIEW(2),
    WAIT_ACCEPT(3),

    //审稿结果状态
    PASS_PAPER(0),
    FAIL_PAPER(1),
    /**
     * submit状态
     * 0-投稿成功，1-退回修改，2-拒收，3-待审稿，4-待分配
     */
    SUBMIT_SUCCESS(0),
    SUBMIT_REVISE(1),
    SUBMIT_COMPLETE_REJECT(2),
    SUBMIT_UNDER_REVIEW(3),
    SUBMIT_WITH_EDITOR(4)
    ;



    private Integer code;

    StatusCodeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }


}
