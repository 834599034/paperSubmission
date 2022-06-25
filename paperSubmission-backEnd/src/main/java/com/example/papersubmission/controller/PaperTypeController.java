package com.example.papersubmission.controller;

import com.example.papersubmission.entity.PaperType;
import com.example.papersubmission.service.PaperTypeService;
import com.example.papersubmission.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("papersubmission/paperType")
public class PaperTypeController {


    @Autowired
    PaperTypeService paperTypeService;

    @GetMapping("/allinfo")
    public CommonResult paperTypeInfo(){
        List<PaperType> list = paperTypeService.list();
        return CommonResult.success(list);
    }
    @GetMapping("/infoById")
    public CommonResult paperTypeInfoById(@RequestParam("typeId") Integer typeId){
        PaperType byId = paperTypeService.getById(typeId);
        return CommonResult.success(byId);
    }


}
