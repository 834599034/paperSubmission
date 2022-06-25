package com.example.papersubmission.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class SubmitPaperVo {

    private Integer submitId;
    /**
     * 论文题目
     */
    private String title;

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

    /**
     * 类型id
     */
    private Integer typeId;
}
