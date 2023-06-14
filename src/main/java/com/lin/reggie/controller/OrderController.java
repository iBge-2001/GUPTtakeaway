package com.lin.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lin.reggie.entity.Category;
import com.lin.reggie.entity.Orders;
import com.lin.reggie.service.OrderService;
import com.lin.reggie.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public Result<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);
        orderService.submit(orders);
        return Result.success("下单成功");
    }
    @GetMapping("/page")
    public Result<Page> getOrder(int page,int pageSize){
        //分页构造器
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        //条件构造器，排序
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //添加排序
        queryWrapper.orderByDesc(Orders::getOrderTime);
        //进行分页查询
        orderService.page(pageInfo,queryWrapper);
        return Result.success(pageInfo);
    }
}