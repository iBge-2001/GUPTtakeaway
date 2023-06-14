package com.lin.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.reggie.dto.SetmealDto;
import com.lin.reggie.entity.Category;
import com.lin.reggie.entity.Setmeal;

public interface SetmealService extends IService<Setmeal> {
    //根据id查询套餐并且查询套餐内的菜品
    public SetmealDto getByIdWithFlavor(Long id);

    //修改套餐
    public void updateWithDish(SetmealDto setmealDto);
}
