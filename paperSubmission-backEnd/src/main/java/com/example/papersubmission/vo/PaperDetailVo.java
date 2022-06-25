package com.example.papersubmission.vo;

import com.example.papersubmission.entity.Client;
import lombok.Data;

import java.util.List;

@Data
public class PaperDetailVo {
    /**
     * 论文题目
     */
    private String title;

    /**
     * 第一作者id
     */
    private Integer firstAuthor;

    /**
     * 第一作者姓名
     */
    private String firstAuthorName;

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

    /**
     * 审稿人信息
     */
    private List<Client> reviewers;
    /**
     * 0-投稿成功，1-退回修改，2-拒收，3-待审稿，4-待分配
     */
    private Integer status;


    private Integer inviteNum;
}
