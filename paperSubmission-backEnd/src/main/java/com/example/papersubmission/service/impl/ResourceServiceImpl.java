package com.example.papersubmission.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.papersubmission.entity.Resource;
import com.example.papersubmission.service.ResourceService;
import com.example.papersubmission.mapper.ResourceMapper;
import org.springframework.stereotype.Service;

/**
* @author h8345
* @description 针对表【resource】的数据库操作Service实现
* @createDate 2022-05-20 18:01:40
*/
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource>
    implements ResourceService{

}




