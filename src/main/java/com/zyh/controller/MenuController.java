package com.zyh.controller;

import com.zyh.entity.ErrorCode;
import com.zyh.entity.Menu;
import com.zyh.entity.Result;
import com.zyh.service.MenuService;
import com.zyh.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author zyh
 * @Date 2022/9/19 21:07
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public Result findMenuList(MenuVo menuVo){
        List<Menu> menuList = menuService.findMenuList(menuVo);
        return Result.success(menuList);
    }

    @GetMapping("/parent/list")
    public Result findMenuParentList(){
        List<Menu> menuList = menuService.findMenuParentList();
        return Result.success(menuList);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Menu menu){
        if (menuService.save(menu)){
            return Result.success("菜单添加成功!");
        }
        return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), "菜单添加失败!");
    }

    @PutMapping("/update")
    public Result update(@RequestBody Menu menu){
        if (menuService.updateById(menu)){
            return Result.success("菜单修改成功!");
        }
        return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), "菜单修改失败!");
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id){
        if (menuService.removeById(id)){
            return Result.success("菜单删除成功!");
        }
        return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), "菜单删除失败!");
    }

    @GetMapping("/check/{id}")
    public Result check(@PathVariable Long id){
        //判断菜单是否有子菜单
        if (menuService.hasChildrenOfMenu(id)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), "该部门下存在子部门，无法删除");
        }
        return Result.success(null);
    }
}
