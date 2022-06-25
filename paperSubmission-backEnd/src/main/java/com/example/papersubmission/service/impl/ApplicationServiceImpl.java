package com.example.papersubmission.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.papersubmission.entity.Application;
import com.example.papersubmission.service.ApplicationService;
import com.example.papersubmission.mapper.ApplicationMapper;
import org.springframework.stereotype.Service;

/**
* @author h8345
* @description 针对表【application】的数据库操作Service实现
* @createDate 2022-05-20 18:00:59
*/
@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application>
    implements ApplicationService{

}




