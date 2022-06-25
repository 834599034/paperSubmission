package com.example.papersubmission.controller;

import com.example.papersubmission.component.HostHolder;
import com.example.papersubmission.entity.Paper;
import com.example.papersubmission.entity.Submit;
import com.example.papersubmission.service.ClientService;
import com.example.papersubmission.service.PaperService;
import com.example.papersubmission.service.SubmitService;
import com.example.papersubmission.service.impl.PaperServiceImpl;
import com.example.papersubmission.service.impl.SubmitServiceImpl;
import com.example.papersubmission.utils.CommonResult;
import com.example.papersubmission.utils.PageUtils;
import com.example.papersubmission.vo.PaperDetailVo;
import com.example.papersubmission.vo.SubmitDetailVo;
import com.example.papersubmission.vo.SubmitPaperVo;
import com.example.papersubmission.vo.SubmitVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("papersubmission/submitPaper")
public class SubmitController {

    @Autowired
    private SubmitService submitService;


    @Autowired
    private HostHolder hostHolder;

    //投稿
    @PostMapping("/contribute")
    public CommonResult contribute(@RequestBody SubmitPaperVo submitPaperVo){
        Integer user = hostHolder.getUser();

        submitService.contribute(submitPaperVo,user);
        return CommonResult.success();
    }
    //重新上传
    @PostMapping("/modify")
    public CommonResult modify(@RequestBody SubmitPaperVo submitPaperVo){
        Integer user = hostHolder.getUser();

        submitService.modify(submitPaperVo,user);
        return CommonResult.success();
    }

    //(普通用户) 获取所有投稿信息
    @GetMapping("/userAllSubmit")
    public CommonResult getSubmitByUser(@RequestParam HashMap<String,Object> params){
        //获取当前登陆信息
        Integer userId = hostHolder.getUser();
        PageUtils submitList=submitService.getSubmitByUser(params,userId);
        return CommonResult.success(submitList);
    }

    //(超管) 查看所有投稿信息
    @GetMapping("/listSubmit")
    public CommonResult listSubmit(@RequestParam HashMap<String,Object> params){
        PageUtils pageUtils=submitService.listSubmit(params);
        return CommonResult.success(pageUtils);
    }


    //获取论文详情
    @GetMapping("/paperDeail")
    public CommonResult paperDetail(@RequestParam("submitId") Integer submitId){
        PaperDetailVo paperDetailVo=submitService.getPaperDeatail(submitId);
        return CommonResult.success(paperDetailVo);
    }

    //(超管)查看当前审稿进度
    @GetMapping("/getSubmitDetail")
    public CommonResult getSubmitDetail(@RequestParam("submitId") Integer submitId){
        SubmitDetailVo submitDetailVo=submitService.getSubmitDetail(submitId);
        return CommonResult.success(submitDetailVo);
    }

    //(超管)整理审稿意见，给出结论
    @PostMapping("/handleSubmit")
    public CommonResult handleSubmit(@RequestBody SubmitDetailVo submitDetailVo){
        submitService.handleSubmit(submitDetailVo);
        return CommonResult.success();
    }

    @GetMapping("/getSuggestion")
    public CommonResult getSuggestion(@RequestParam Integer submitId){
        Submit byId = submitService.getById(submitId);
        return CommonResult.success(byId);
    }

    @PostMapping("/rejectPaper")
    public CommonResult rejectPaper(@RequestBody SubmitDetailVo submitDetailVo){
        submitService.rejectPaper(submitDetailVo);
        return CommonResult.success();
    }
}
