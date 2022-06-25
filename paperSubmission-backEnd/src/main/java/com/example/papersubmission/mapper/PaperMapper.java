package com.example.papersubmission.mapper;

import com.example.papersubmission.entity.Paper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author h8345
* @description 针对表【paper】的数据库操作Mapper
* @createDate 2022-05-20 18:01:20
* @Entity com.example.papersubmission.entity.Paper
*/
@Mapper
public interface PaperMapper extends BaseMapper<Paper> {

}




