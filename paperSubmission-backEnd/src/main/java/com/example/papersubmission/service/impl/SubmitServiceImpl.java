package com.example.papersubmission.service.impl;

import com.aliyun.oss.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.papersubmission.entity.*;
import com.example.papersubmission.exceptions.UnReviewException;
import com.example.papersubmission.mapper.ReviewerInviteMapper;
import com.example.papersubmission.service.*;
import com.example.papersubmission.mapper.SubmitMapper;
import com.example.papersubmission.utils.PageUtils;
import com.example.papersubmission.utils.Query;
import com.example.papersubmission.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.papersubmission.constant.StatusCodeEnum.*;

/**
* @author h8345
* @description 针对表【submit】的数据库操作Service实现
* @createDate 2022-05-20 18:01:52
*/
@Service
public class SubmitServiceImpl extends ServiceImpl<SubmitMapper, Submit>
    implements SubmitService{

    @Autowired
    private PaperService paperService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private PaperTypeService paperTypeService;

    @Autowired
    private ReviewerInviteMapper reviewerInviteMapper;



    @Transactional
    @Override
    public void contribute(SubmitPaperVo submitPaperVo, Integer user) {
        Paper paper=new Paper();
        Submit submit=new Submit();
        BeanUtils.copyProperties(submitPaperVo,paper);
        paperService.save(paper);
        submit.setPaperId(paper.getId());
        submit.setUserId(user);
        submit.setTypeId(submitPaperVo.getTypeId());
        this.save(submit);
    }

    @Override
    public PageUtils getSubmitByUser(HashMap<String, Object> params, Integer userId) {
        IPage<Submit> page = this.page(new Query<Submit>().getPage(params), new QueryWrapper<Submit>().eq("user_id", userId));
        List<Submit> submits = page.getRecords();
        List<SubmitVo> collect = submits.stream().map(submit -> {
            SubmitVo submitVo = new SubmitVo();
            BeanUtils.copyProperties(submit, submitVo);
            //设置SubmitID
            submitVo.setSubmitId(submit.getId());
            //获取论文题目
            Integer paperId = submit.getPaperId();
            Paper byId = paperService.getById(paperId);
            submitVo.setTitle(byId.getTitle());

            Client firstAuthor = clientService.getById(byId.getFirstAuthor());
            submitVo.setFirstAuthor(firstAuthor.getRealName());
            //获取论文类型
            Integer typeId = submit.getTypeId();
            PaperType paperType = paperTypeService.getById(typeId);
            submitVo.setType(paperType.getTypeName());
            return submitVo;
        }).collect(Collectors.toList());
        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(collect);

        return pageUtils;
    }

    @Override
    public PageUtils listSubmit(HashMap<String, Object> params) {
        if(!StringUtils.isNullOrEmpty((String) params.get("status"))){
            QueryWrapper<Submit> eq=new QueryWrapper<Submit>();
            switch (Integer.parseInt((String) params.get("status"))){
                //投稿成功
                case 10:
                    eq.eq("status", 0);
                    break;
                //正在审稿中
                case 11:
                    eq.eq("status", 3).or().eq("status",4);
                    break;
                //审稿结束
                case 12:
                    Integer[] status={2,5};
                    eq.in("status", Arrays.asList(status));
                    break;
                case 13:
                    eq.eq("status",1);
                    break;
            }
            IPage<Submit> page = this.page(new Query<Submit>().getPage(params), eq);
            List<Submit> records = page.getRecords();

            List<AdminSubmitVo> adminSubmitVos = records.stream().map(submit -> {
                AdminSubmitVo adminSubmitVo = new AdminSubmitVo();
                BeanUtils.copyProperties(submit, adminSubmitVo);
                //设置SubmitID
                adminSubmitVo.setSubmitId(submit.getId());
                //获取论文题目
                Integer paperId = submit.getPaperId();
                Paper byId = paperService.getById(paperId);
                adminSubmitVo.setTitle(byId.getTitle());

                //获取第一作者姓名
                Client firstAuthor = clientService.getById(byId.getFirstAuthor());
                adminSubmitVo.setFirstAuthor(firstAuthor.getRealName());

                //获取论文类型
                Integer typeId = submit.getTypeId();
                PaperType paperType = paperTypeService.getById(typeId);
                adminSubmitVo.setType(paperType.getTypeName());

                //如果状态在审稿中，查询审稿人信息
                if (submit.getStatus() == SUBMIT_UNDER_REVIEW.getCode()||submit.getStatus()==SUBMIT_WITH_EDITOR.getCode()) {
                    List<ReviewerInvite> reviewerInvites =reviewerInviteMapper.selectList(new QueryWrapper<ReviewerInvite>().eq("submit_id", submit.getId()));
                    List<Client> reviewerCollect = reviewerInvites.stream().map(reviewerInvite -> {
                        Client client = clientService.getById(reviewerInvite.getReviewerId());
                        return client;
                    }).collect(Collectors.toList());
                    adminSubmitVo.setReviewers(reviewerCollect);
                }
                return adminSubmitVo;
            }).collect(Collectors.toList());
             PageUtils pageUtils=new PageUtils(page);
            pageUtils.setList(adminSubmitVos);
            return pageUtils;
        }

//        records.stream().map()
        return null;
    }

    @Override
    public PaperDetailVo getPaperDeatail(Integer submitId) {
        PaperDetailVo paperDetailVo = new PaperDetailVo();
        Submit submit = this.getById(submitId);
        Paper paper = paperService.getById(submit.getPaperId());
        BeanUtils.copyProperties(paper,paperDetailVo);
        paperDetailVo.setInviteNum(submit.getInviteNum());
        Client user = clientService.getById(paper.getFirstAuthor());
        paperDetailVo.setFirstAuthorName(user.getRealName());
        paperDetailVo.setStatus(submit.getStatus());
        //如果状态在审稿中，查询审稿人信息
        if (submit.getStatus() == SUBMIT_UNDER_REVIEW.getCode()) {
            List<ReviewerInvite> reviewerInvites =reviewerInviteMapper.selectList(new QueryWrapper<ReviewerInvite>().eq("submit_id", submit.getId()));
            List<Client> reviewerCollect = reviewerInvites.stream().map(reviewerInvite -> {
                Client client = clientService.getById(reviewerInvite.getReviewerId());
                return client;
            }).collect(Collectors.toList());
           paperDetailVo.setReviewers(reviewerCollect);
        }

        return paperDetailVo;
    }

    @Override
    public SubmitDetailVo getSubmitDetail(Integer submitId) {
        Submit submit = this.getById(submitId);
        SubmitDetailVo submitDetailVo = new SubmitDetailVo();
        List<ReviewerInvite> reviewerInvites = this.reviewerInviteMapper.selectList(new QueryWrapper<ReviewerInvite>().eq("submit_id", submitId).ne("status",REJECT_INVITE.getCode()));
        submitDetailVo.setSubmitId(submit.getId());
        if(reviewerInvites!=null){
            List<ReviewDetailVo> collect = reviewerInvites.stream().map(reviewerInvite -> {
                ReviewDetailVo reviewDetailVo=new ReviewDetailVo();
                Client reviewer= this.clientService.getById(reviewerInvite.getReviewerId());
                reviewDetailVo.setReviewerId(reviewer.getId());
                reviewDetailVo.setReviewerRealName(reviewer.getRealName());
                reviewDetailVo.setIcon(reviewer.getIcon());

                BeanUtils.copyProperties(reviewerInvite, reviewDetailVo);
                return reviewDetailVo;
            }).collect(Collectors.toList());
            submitDetailVo.setReviewDetailVos(collect);
        }
        return submitDetailVo;
    }

    @Override
    public void handleSubmit(SubmitDetailVo submitDetailVo) {
        Submit submit = this.getById(submitDetailVo.getSubmitId());
        submit.setStatus(submitDetailVo.getStatus());
        submit.setSuggestion(submitDetailVo.getSuggestion());
        List<ReviewerInvite> reviewerInvites = this.reviewerInviteMapper.selectList(new QueryWrapper<ReviewerInvite>().eq("submit_id", submitDetailVo.getSubmitId()));
        List<ReviewerInvite> collect = reviewerInvites.stream().filter(reviewerInvite -> reviewerInvite.getStatus() == FINISH_REVIEW.getCode()).collect(Collectors.toList());
        if(collect.size()!=submit.getTotalInvite()){
            throw new UnReviewException();
        }
        this.updateById(submit);
    }

    @Override
    public void modify(SubmitPaperVo submitPaperVo, Integer user) {
        Submit submit=this.getById(submitPaperVo.getSubmitId());
        Paper paper=this.paperService.getById(submit.getPaperId());
        BeanUtils.copyProperties(submitPaperVo,paper);
        paperService.updateById(paper);
        submit.setPaperId(paper.getId());
        submit.setUserId(user);
        submit.setTypeId(submitPaperVo.getTypeId());
        submit.setStatus(3);
        this.updateById(submit);
    }

    @Override
    public void rejectPaper(SubmitDetailVo submitDetailVo) {
        Submit byId = this.getById(submitDetailVo.getSubmitId());
        byId.setStatus(SUBMIT_COMPLETE_REJECT.getCode());
        byId.setSuggestion(submitDetailVo.getSuggestion());
        this.updateById(byId);
    }
}




