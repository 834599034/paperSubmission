package com.example.papersubmission.service;

import com.example.papersubmission.entity.ReviewerInvite;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.papersubmission.utils.PageUtils;
import com.example.papersubmission.vo.*;

import java.util.HashMap;

/**
* @author h8345
* @description 针对表【reviewer_invite】的数据库操作Service
* @createDate 2022-05-20 18:01:46
*/
public interface ReviewerInviteService extends IService<ReviewerInvite> {


    void assignPaper(ReviewerInviteVo reviewerInviteVo);

    void handleInvite(HandleInviteVo handleInviteVo);

    void reviewPaper(HandleInviteVo handleInviteVo);
    PageUtils listReviewedPaper(HashMap<String, Object> params, Integer user);

    AssignVo asignedReviewers(Integer submitId);

    void deleteAssignedReviewers(ReviewerInviteVo reviewerInviteVo);

    PageUtils getInviteInfo(HashMap<String, Object> params, Integer user);
}
