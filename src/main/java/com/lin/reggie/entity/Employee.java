package com.lin.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.apache.ibatis.javassist.SerialVersionUID;

import java.io.Serializable;
import java.time.LocalDateTime;
/*
* 员工实体
* */
@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 1727794184839181233L;
    private Long id;

    private String username;

    private String name;

    private String password;

    private String phone;

    private String sex;

    private String idNumber;//身份证

    private Integer status;

    @TableField(fill = FieldFill.INSERT)//MP自带的注解，自动填充
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}
