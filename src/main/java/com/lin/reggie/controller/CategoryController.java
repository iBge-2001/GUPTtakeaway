package com.lin.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lin.reggie.common.Result;
import com.lin.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.lin.reggie.entity.Category;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping()
    public Result<String> save (@RequestBody Category category){
        log.info("category:{}");
        categoryService.save(category);
        return Result.success("新增分类成功");
    }
    @GetMapping("/page")
    public Result<Page> pageResult(int page,int pageSize){
        //分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);
        //条件构造器，排序
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加排序
        queryWrapper.orderByDesc(Category::getSort);
        //进行分页查询
        categoryService.page(pageInfo,queryWrapper);
        return Result.success(pageInfo);
    }

    /**
     * 根据id删除分类
     * @param id
     * @return
     */
    @DeleteMapping()
    public Result<String> delete(Long id ){
        log.info("商品id{}",id);
//        categoryService.removeById(id);
        categoryService.remove(id);
        return Result.success("分类信息删除成功");
    }
    @PutMapping()
    public Result<String> updateByCategoryId(@RequestBody Category category){
        log.info("分类id：{}",category.getId());
        categoryService.updateById(category);
        return Result.success("修改成功");
    }
    /**
     * 根据条件查询分类数据
     * @param category
     * @return
     */
    @GetMapping("/list")
    public Result<List<Category>> list(Category category){
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);
        return Result.success(list);
    }
}
