package com.example.papersubmission.service.impl;

import com.aliyun.oss.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.papersubmission.entity.*;
import com.example.papersubmission.exceptions.NoEnoughInviteNumException;
import com.example.papersubmission.mapper.SubmitMapper;
import com.example.papersubmission.service.ClientService;
import com.example.papersubmission.service.PaperService;
import com.example.papersubmission.service.PaperTypeService;
import com.example.papersubmission.service.ReviewerInviteService;
import com.example.papersubmission.mapper.ReviewerInviteMapper;
import com.example.papersubmission.utils.PageUtils;
import com.example.papersubmission.utils.Query;
import com.example.papersubmission.vo.*;
import io.goeasy.GoEasy;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.papersubmission.constant.StatusCodeEnum.*;

/**
* @author h8345
* @description 针对表【reviewer_invite】的数据库操作Service实现
* @createDate 2022-05-20 18:01:46
*/
@Service
public class ReviewerInviteServiceImpl extends ServiceImpl<ReviewerInviteMapper, ReviewerInvite>
    implements ReviewerInviteService{

    @Autowired
    private PaperService paperService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private SubmitMapper submitMapper;

    @Autowired
    private PaperTypeService paperTypeService;


    @Autowired
    GoEasy goEasy;


    //分配论文
    @Transactional
    @Override
    public void assignPaper(ReviewerInviteVo reviewerInviteVo) {
        List<Integer> reviewerId = reviewerInviteVo.getReviewerId();
        Submit byId = this.submitMapper.selectById(reviewerInviteVo.getSubmitId());
        //查看剩余邀请人数
        Integer inviteNum = byId.getInviteNum();
        if(reviewerId.size()>inviteNum){
            throw new NoEnoughInviteNumException();
        }
        if(inviteNum==0){
            throw new NoEnoughInviteNumException();
        }
        List<ReviewerInvite> collect = reviewerId.stream().map(reviewer -> {
            ReviewerInvite reviewerInvite = new ReviewerInvite();
            reviewerInvite.setSubmitId(reviewerInviteVo.getSubmitId());
            reviewerInvite.setPaperId(byId.getPaperId());
            reviewerInvite.setReviewerId(reviewer);
            byId.setInviteNum(byId.getInviteNum() - 1);
            return reviewerInvite;
        }).collect(Collectors.toList());
        byId.setStatus(SUBMIT_WITH_EDITOR.getCode());
        submitMapper.updateById(byId);
        this.saveBatch(collect);
        reviewerId.stream().forEach(reviewer ->{
            String channel= new String("my_channel" + reviewer);
            Paper paper = paperService.getById(byId.getPaperId());
            String content=new String("邀请您审阅论文:"+paper.getTitle());
            goEasy.publish(channel,content);
        });

    }

    //(审稿人)处理邀约
    @Override
    @Transactional
    public void handleInvite(HandleInviteVo handleInviteVo) {
        ReviewerInvite reviewerInvite = this.getById(handleInviteVo.getReviewerInviteId());

        Submit submit = submitMapper.selectById(reviewerInvite.getSubmitId());

//        ReviewerInvite reviewerInvite = this.getOne(new QueryWrapper<ReviewerInvite>().eq("submit_id", handleInviteVo.getSubmitId()));
        //接受邀请
        if(handleInviteVo.getResult()==0){
            reviewerInvite.setStatus(ACCEPT_INVITE.getCode());
            submit.setAcceptInvite(submit.getAcceptInvite()+1);
        }else {
            reviewerInvite.setStatus(REJECT_INVITE.getCode());
            submit.setInviteNum(submit.getInviteNum()+1);
        }
        if(submit.getAcceptInvite()==submit.getTotalInvite()){
            submit.setStatus(SUBMIT_UNDER_REVIEW.getCode());
        }
        submitMapper.updateById(submit);
        this.updateById(reviewerInvite);
    }


    //审稿
    @Override
    public void reviewPaper(HandleInviteVo handleInviteVo) {
//        ReviewerInvite reviewerInvite = this.getOne(new QueryWrapper<ReviewerInvite>().eq("paper_id", handleInviteVo.getPaperId()));
        ReviewerInvite reviewerInvite = this.getById(handleInviteVo.getReviewerInviteId());
        reviewerInvite.setSuggestion(handleInviteVo.getSuggestion());
        if(handleInviteVo.getConclusion()==0){
            reviewerInvite.setConclusion(PASS_PAPER.getCode());
        }else {
            reviewerInvite.setConclusion(FAIL_PAPER.getCode());
        }
        reviewerInvite.setStatus(FINISH_REVIEW.getCode());
        this.updateById(reviewerInvite);
    }


    //(审稿人)查看论文
    @Override
    public PageUtils listReviewedPaper(HashMap<String, Object> params, Integer user) {
        if(!StringUtils.isNullOrEmpty((String) params.get("status"))){
            QueryWrapper<ReviewerInvite> queryWrapper=new QueryWrapper<ReviewerInvite>();
            if(Integer.parseInt((String) params.get("status"))!=100){
                queryWrapper.eq("status", params.get("status"));
            }
            IPage<ReviewerInvite> page=this.page(new Query<ReviewerInvite>().getPage(params),queryWrapper);
            List<ReviewerInvite> records = page.getRecords();
            List<InviteInfoVo> collect = records.stream().map(reviewerInvite -> {
                InviteInfoVo inviteInfoVo=new InviteInfoVo();
                inviteInfoVo.setReviewerInviteId(reviewerInvite.getId());
                BeanUtils.copyProperties(reviewerInvite, inviteInfoVo);
                Paper paper = paperService.getById(reviewerInvite.getPaperId());
                Submit submit = this.submitMapper.selectById(reviewerInvite.getSubmitId());
                PaperType paperType = this.paperTypeService.getById(submit.getTypeId());
                inviteInfoVo.setType(paperType.getTypeName());
                inviteInfoVo.setTitle(paper.getTitle());
                return inviteInfoVo;
            }).collect(Collectors.toList());
            PageUtils pageUtils=new PageUtils(page);
            pageUtils.setList(collect);
            return pageUtils;
        }
        return null;
    }

    @Override
    public AssignVo  asignedReviewers(Integer submitId) {
        List<ReviewerInvite> submit_id = this.list(new QueryWrapper<ReviewerInvite>().eq("submit_id", submitId));
        List<AsignedReviewerVo> collect = submit_id.stream().filter(reviewerInvite -> reviewerInvite.getStatus()!=1).map(reviewerInvite -> {
            Client byId = clientService.getById(reviewerInvite.getReviewerId());
            AsignedReviewerVo asignedReviewerVo = new AsignedReviewerVo();
            BeanUtils.copyProperties(byId, asignedReviewerVo);
            asignedReviewerVo.setInviteStatus(reviewerInvite.getStatus());
            return asignedReviewerVo;
        }).collect(Collectors.toList());
        AssignVo asignVo = new AssignVo();
        asignVo.setReviewerVos(collect);
        Submit submit = this.submitMapper.selectById(submitId);
        asignVo.setInviteNum(submit.getInviteNum());
        return asignVo;
    }

    @Transactional
    @Override
    public void deleteAssignedReviewers(ReviewerInviteVo reviewerInviteVo) {
        List<ReviewerInvite> submit_id = this.list(new QueryWrapper<ReviewerInvite>().eq("submit_id", reviewerInviteVo.getSubmitId()));
        //转set
        Set<Integer> collect = reviewerInviteVo.getReviewerId().stream().collect(Collectors.toSet());
        //获取需要删除的邀约
        List<ReviewerInvite> deleteReviewers = submit_id.stream().filter(reviewerInvite -> collect.contains((Integer) reviewerInvite.getReviewerId())).collect(Collectors.toList());
        //更新剩余邀请人数
        Submit submit = this.submitMapper.selectById(reviewerInviteVo.getSubmitId());
        submit.setInviteNum(submit.getInviteNum()+deleteReviewers.size());
        this.submitMapper.updateById(submit);
        //删除邀约
        this.removeBatchByIds(deleteReviewers);
        deleteReviewers.stream().forEach(reviewer ->{
            String channel= new String("my_channel" + reviewer);
            Paper paper=paperService.getById(submit.getPaperId());
            String content=new String(paper.getTitle()+"的审稿邀请已被编辑取消！");
            goEasy.publish(channel,content);
        });
    }

    @Override
    public PageUtils getInviteInfo(HashMap<String, Object> params, Integer user) {
        IPage<ReviewerInvite> page=this.page(new Query<ReviewerInvite>().getPage(params),new QueryWrapper<ReviewerInvite>().eq("reviewer_id", user).eq("status",WAIT_ACCEPT.getCode()));
        List<InviteInfoVo> collect = page.getRecords().stream().map(reviewerInvite -> {
            InviteInfoVo inviteInfoVo = new InviteInfoVo();
            inviteInfoVo.setReviewerInviteId(reviewerInvite.getId());
            Submit submit = this.submitMapper.selectById(reviewerInvite.getSubmitId());
            BeanUtils.copyProperties(reviewerInvite, inviteInfoVo);
            Paper byId = this.paperService.getById(reviewerInvite.getPaperId());
            BeanUtils.copyProperties(byId, inviteInfoVo);
            PaperType paperType = this.paperTypeService.getById(submit.getTypeId());
            inviteInfoVo.setType(paperType.getTypeName());
            return inviteInfoVo;
        }).collect(Collectors.toList());
        PageUtils pageUtils=new PageUtils(page);
        pageUtils.setList(collect);
        return pageUtils;
    }


}




