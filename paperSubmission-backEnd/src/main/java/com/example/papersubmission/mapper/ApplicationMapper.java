package com.example.papersubmission.mapper;

import com.example.papersubmission.entity.Application;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author h8345
* @description 针对表【application】的数据库操作Mapper
* @createDate 2022-05-20 18:00:59
* @Entity com.example.papersubmission.entity.Application
*/
@Mapper
public interface ApplicationMapper extends BaseMapper<Application> {

}




