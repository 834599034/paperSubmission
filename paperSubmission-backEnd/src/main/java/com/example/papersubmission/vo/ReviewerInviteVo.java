package com.example.papersubmission.vo;

import lombok.Data;

import java.util.List;

@Data
public class ReviewerInviteVo {
    /**
     * 投稿 id
     */
    private Integer submitId;

    /**
     * 审稿人id
     */
    private List<Integer> reviewerId;
}
