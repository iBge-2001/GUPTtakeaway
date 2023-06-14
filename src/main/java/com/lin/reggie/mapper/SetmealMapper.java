package com.lin.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.reggie.entity.Category;
import com.lin.reggie.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
}
