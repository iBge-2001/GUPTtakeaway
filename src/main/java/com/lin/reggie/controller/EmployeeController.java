package com.lin.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lin.reggie.common.Result;
import com.lin.reggie.entity.Employee;
import com.lin.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /*
    * 登录
    * */
    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee user){

    //获取页面提交过来的密码
        String password = user.getPassword();
        //调工具类md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //根据用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //eq(实体类：：查询字段，条件值)相当于where
        queryWrapper.eq(Employee::getUsername,user.getUsername());
        Employee one = employeeService.getOne(queryWrapper);
        //判断是否为空
        if (one ==null){
            return Result.error("登录失败");
        }
        //比较密码是否一致
        if (!one.getPassword().equals(password)) {
            return Result.error("登陆失败");
        }
        //密码正确查看user的status是0还是1
        if(one.getStatus()==0){
            //控制台打印
            System.out.println("账户呗禁用");
            return Result.error("账号被禁用");
        }
        else{
            request.getSession().setAttribute("user",one.getId());
            return Result.success(one);
        }
    }
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return Result.success("退出成功");
    }

    //新增员工
    @PostMapping()
    public Result<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工信息：{}",employee.toString());
        //默认密码123456，使用md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setCreateUser((long)request.getSession().getAttribute("user"));
//        employee.setUpdateUser((long)request.getSession().getAttribute("user"));
        employeeService.save(employee);
            return Result.success("新增员工成功");
    }
    /**
     * 员工信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<Page> pageResult(int page,int pageSize,String name ){
        log.info("page = {},pageSize = {}, name = {}",page,pageSize,name);
        //构造页面构造器
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getCreateTime);
        //执行查询
        employeeService.page(pageInfo,queryWrapper);
        return Result.success(pageInfo);
    }

    @PutMapping()
    public Result<String> update(HttpServletRequest request, @RequestBody Employee employee){
        log.info(employee.toString());
        //获得线程id
        long id = Thread.currentThread().getId();
        log.info("线程id为{}",id);
//        long user = (long)request.getSession().getAttribute("user");
//        employee.setUpdateUser(user);
//        employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        return Result.success("员工信息修改成功");
    }
    @GetMapping("/{id}")
    public Result<Employee> getByid(@PathVariable long id ){
            log.info("根据id{}查找员工",id);
        Employee byId = employeeService.getById(id);
        if (byId!=null){
            return Result.success(byId);
        }
        else {
            return Result.error("查无此人");
        }
    }
}
