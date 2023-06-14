package com.lin.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User>{
}
