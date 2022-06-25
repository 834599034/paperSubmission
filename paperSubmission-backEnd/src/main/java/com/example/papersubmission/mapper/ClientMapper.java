package com.example.papersubmission.mapper;

import com.example.papersubmission.entity.Client;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author h8345
* @description 针对表【client】的数据库操作Mapper
* @createDate 2022-05-21 20:55:59
* @Entity com.example.papersubmission.entity.Client
*/
@Mapper
public interface ClientMapper extends BaseMapper<Client> {

}




