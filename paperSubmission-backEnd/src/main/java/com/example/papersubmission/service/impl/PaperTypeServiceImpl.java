package com.example.papersubmission.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.papersubmission.entity.PaperType;
import com.example.papersubmission.service.PaperTypeService;
import com.example.papersubmission.mapper.PaperTypeMapper;
import org.springframework.stereotype.Service;

/**
* @author h8345
* @description 针对表【paper_type】的数据库操作Service实现
* @createDate 2022-05-20 18:01:32
*/
@Service
public class PaperTypeServiceImpl extends ServiceImpl<PaperTypeMapper, PaperType>
    implements PaperTypeService{

}




