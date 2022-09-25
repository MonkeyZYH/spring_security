package com.zyh.DTO;

import lombok.Data;

import java.util.List;

/**
 * @Author zyh
 * @Date 2022/9/25 10:20
 */
@Data
public class RoleMenuDTO {
    //角色编号
    private Long roleId;

    //权限菜单ID集合
    private List<Long> list;
}
