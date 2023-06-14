package com.lin.reggie.dto;

import com.lin.reggie.entity.Setmeal;
import com.lin.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
