package com.lin.reggie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.reggie.dto.DishDto;
import com.lin.reggie.dto.SetmealDto;
import com.lin.reggie.entity.*;
import com.lin.reggie.mapper.CategoryMapper;
import com.lin.reggie.mapper.SetmealMapper;
import com.lin.reggie.service.CategoryService;
import com.lin.reggie.service.SetmealDishService;
import com.lin.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @Override
    public SetmealDto getByIdWithFlavor(Long id) {
        //查询菜品基本信息，从dish表查询
        Setmeal setmeal = this.getById(id);

        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal,setmealDto);

        //查询当前菜品对应的菜品信息，从setmealdishs表查询
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,setmeal.getId());
        List<SetmealDish> setmealDishes = setmealDishService.list(queryWrapper);
        setmealDto.setSetmealDishes(setmealDishes);
        return setmealDto;
    }




    public void updateWithDish(SetmealDto setmealDto){
        //更新setmeal表
        this.updateById(setmealDto);

        //清理当前套餐中的菜品
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());
        setmealDishService.remove(queryWrapper);
        //添加当前提交过来的菜品执行setmealdish的insert操作
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }


}
