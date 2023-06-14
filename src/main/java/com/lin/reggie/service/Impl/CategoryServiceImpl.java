package com.lin.reggie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.reggie.common.CustomException;
import com.lin.reggie.entity.Dish;
import com.lin.reggie.entity.Setmeal;
import com.lin.reggie.mapper.CategoryMapper;
import com.lin.reggie.service.CategoryService;
import com.lin.reggie.service.DishService;
import com.lin.reggie.service.SetmealService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lin.reggie.entity.Category;
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    @Override
    /**
     * 根据id删除分类，删除前需要判断
     */
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        //查询当前分类是否关联了菜品，如果已关联，抛出一个业务异常
        if (count1 >0) {
            //关联菜品，抛出异常
            throw  new CustomException("该分类关联了菜品，请先删除该分类下的菜品");
        }
        //查询当前分类是否关联了套餐，如果已Setmeal，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if (count2 > 0){
            //关联套餐，抛出异常
           throw  new CustomException("该分类关联了套餐，请先删除该分类下的套餐");
        }
        //正常删除分类
        super.removeById(id);
    }
    }
