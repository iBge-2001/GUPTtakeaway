package com.lin.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.reggie.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;

public interface CategoryService extends IService<Category> {

    public void remove(Long id);
}
