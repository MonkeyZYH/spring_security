package com.zyh.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyh.entity.ErrorCode;
import com.zyh.entity.Result;
import com.zyh.entity.Role;
import com.zyh.entity.User;
import com.zyh.service.MenuService;
import com.zyh.service.RoleService;
import com.zyh.vo.RoleMenuVo;
import com.zyh.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author zyh
 * @Date 2022/9/22 17:53
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    /**
     *查询角色列表
     * @param roleVo
     * @return
     */
    @GetMapping("/list")
    public Result list(RoleVo roleVo){
        //创建分页对象
        Page<Role> page = new Page<>(roleVo.getPageNo(),roleVo.getPageSize());
        roleService.findRoleListByUserId(page,roleVo);
        return Result.success(page);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Role role){
        //调用添加方法
        if (roleService.save(role)){
            return Result.success("角色添加成功!");
        }
        return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), "角色添加失败!");
    }

    @PutMapping("/update")
    public Result update(@RequestBody Role role){
        if (roleService.updateById(role)){
            return Result.success("角色修改成功!");
        }
        return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), "角色修改失败!");
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id){
        if (roleService.deleteRoleById(id)){
            return Result.success("角色删除成功!");
        }
        return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), "角色删除失败!");
    }

    @GetMapping("/check/{id}")
    public Result check(@PathVariable Long id){
        if (roleService.hashRoleCount(id)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), "该角色已分配给其他用户使用，无法删除");
        }
        return Result.success(null);
    }

    /**
     * 分配权限-查询权限树数据
     * @param userId
     * @param roleId
     * @return
     */
    @GetMapping("/getAssignMenuTree")
    public Result getAssignPermissionTree(Long userId, Long roleId) {
        //调用查询权限树数据的方法
        RoleMenuVo menuTree = menuService.findMenuTree(userId, roleId);
        //返回数据
        return Result.success(menuTree);
    }
}
