package com.example.papersubmission.service;

import com.example.papersubmission.entity.Submit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.papersubmission.utils.PageUtils;
import com.example.papersubmission.vo.PaperDetailVo;
import com.example.papersubmission.vo.SubmitDetailVo;
import com.example.papersubmission.vo.SubmitPaperVo;

import java.util.HashMap;

/**
* @author h8345
* @description 针对表【submit】的数据库操作Service
* @createDate 2022-05-20 18:01:52
*/
public interface SubmitService extends IService<Submit> {

    void contribute(SubmitPaperVo submitPaperVo, Integer user);

    PageUtils getSubmitByUser(HashMap<String, Object> params, Integer userId);

    PageUtils listSubmit(HashMap<String, Object> params);

    PaperDetailVo getPaperDeatail(Integer submitId);

    SubmitDetailVo getSubmitDetail(Integer submitId);

    void handleSubmit(SubmitDetailVo submitDetailVo);

    void modify(SubmitPaperVo submitPaperVo, Integer user);

    void rejectPaper(SubmitDetailVo submitId);
}
