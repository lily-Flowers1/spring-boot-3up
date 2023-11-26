package com.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.entity.dto.StuUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StuUserMapper extends BaseMapper<StuUser> {
}
