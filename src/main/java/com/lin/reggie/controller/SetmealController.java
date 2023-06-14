package com.lin.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lin.reggie.common.Result;
import com.lin.reggie.dto.DishDto;
import com.lin.reggie.dto.SetmealDto;
import com.lin.reggie.entity.Category;
import com.lin.reggie.entity.Dish;
import com.lin.reggie.entity.Setmeal;
import com.lin.reggie.service.CategoryService;
import com.lin.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public Result<SetmealDto> getById(@PathVariable Long id){
        SetmealDto byIdWithFlavor = setmealService.getByIdWithFlavor(id);
        return Result.success(byIdWithFlavor);
    }


    @GetMapping("/page")
    public Result<Page> pageResult(int page,int pageSize,String name){
        //构造分页构造器对象
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();

        //条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null,Setmeal::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        //执行分页查询
        setmealService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,setmealDtoPage,"records");

        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();

            BeanUtils.copyProperties(item,setmealDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            if(category != null){
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(list);

        return Result.success(setmealDtoPage);
    }
        @PutMapping()
    public Result<String> update(@RequestBody SetmealDto setmealDto){
        setmealService.updateWithDish(setmealDto);
        return Result.success("修改成功");
        }
}
