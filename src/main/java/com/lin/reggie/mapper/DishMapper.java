package com.lin.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.reggie.entity.Category;
import com.lin.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
