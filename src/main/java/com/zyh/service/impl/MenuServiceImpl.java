package com.zyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyh.entity.Menu;
import com.zyh.mapper.MenuMapper;
import com.zyh.service.MenuService;
import com.zyh.utils.MenuTree;
import com.zyh.vo.MenuVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zyh
 * @Date 2022/9/19 20:45
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Override
    public List<Menu> findMenuList(MenuVo menuVo) {
        List<Menu> menuList = baseMapper.selectList(null);
        List<Menu> menuTree = MenuTree.makeMenuTree(menuList, 0L);
        return menuTree;
    }

    @Override
    public List<Menu> findMenuParentList() {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        List<Menu> menuList = baseMapper.selectList(null);
        Menu menu = new Menu();
        menu.setId(0L);
        menu.setParentId(-1L);
        menu.setMenuName("顶级菜单");
        menuList.add(menu);
        //生成菜单数据
        List<Menu> menuTree = MenuTree.makeMenuTree(menuList, -1L);
        return menuTree;

    }

    @Override
    public boolean hasChildrenOfMenu(Long id) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,id);
        if (baseMapper.selectCount(queryWrapper)>0){
            return true;
        }
        return false;
    }

}
