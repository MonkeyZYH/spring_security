package com.zyh.vo;

import com.zyh.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zyh
 * @Date 2022/9/24 18:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuVo {
    /**
     * 菜单数据
     */
    private List<Menu> menuList=new ArrayList<Menu>();

    /**
     * 该角色拥有的权限菜单数据
     */
    private Object[] checkList;
}
