package com.example.papersubmission.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.papersubmission.component.HostHolder;
import com.example.papersubmission.entity.ReviewerInvite;
import com.example.papersubmission.service.ReviewerInviteService;
import com.example.papersubmission.utils.CommonResult;
import com.example.papersubmission.utils.PageUtils;
import com.example.papersubmission.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("papersubmission/reviewerInvite")
public class ReviewerInviteController {

    @Autowired
    private ReviewerInviteService reviewerInviteService;

    @Autowired
    private HostHolder hostHolder;

    //分配论文(超管)
    @PostMapping("/assignPaper")
    public CommonResult assignPaper(@RequestBody ReviewerInviteVo reviewerInviteVo){
        reviewerInviteService.assignPaper(reviewerInviteVo);
        return CommonResult.success();
    }

    //(审稿人)查看审稿邀请
    @GetMapping("/getInviteInfo")
    public CommonResult getInvite(@RequestParam HashMap<String,Object> params){
        //获取当前用户id
        Integer user = hostHolder.getUser();
        PageUtils pageUtils=this.reviewerInviteService.getInviteInfo(params,user);
        return  CommonResult.success(pageUtils);
    }

    //(审稿人)处理审稿邀请
    @PostMapping("/handleInvite")
    private CommonResult hanleInvite(@RequestBody HandleInviteVo handleInviteVo){
        reviewerInviteService.handleInvite(handleInviteVo);
        return CommonResult.success();
    }

    //(审稿人)审稿
    @PostMapping("/reviewPaper")
    private CommonResult reviewPaper(@RequestBody  HandleInviteVo handleInviteVo){
        reviewerInviteService.reviewPaper(handleInviteVo);
        return CommonResult.success();
    }

    //(审稿人)查看审稿论文
    @GetMapping("/listReviewedPaper")
    private CommonResult listReviewedPaper(@RequestParam HashMap<String,Object> params){
        Integer user = hostHolder.getUser();
        PageUtils reviewedPaperVos = reviewerInviteService.listReviewedPaper(params,user);
        return CommonResult.success(reviewedPaperVos);
    }


    //已分配的审稿人
    @GetMapping("/asignedReviewers")
    private CommonResult asignedReviewers(@RequestParam("submitId") Integer submitId){
        AssignVo asignVo =this.reviewerInviteService.asignedReviewers(submitId);
        return CommonResult.success(asignVo);
    }


    @PostMapping("/deleteAssignedReviewers")
    private CommonResult deleteAssignedReviewers(@RequestBody ReviewerInviteVo reviewerInviteVo){
        reviewerInviteService.deleteAssignedReviewers(reviewerInviteVo);
        return CommonResult.success();
    }





}
