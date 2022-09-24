package com.zyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyh.entity.Menu;
import com.zyh.entity.User;
import com.zyh.mapper.MenuMapper;
import com.zyh.mapper.UserMapper;
import com.zyh.service.MenuService;
import com.zyh.utils.MenuTree;
import com.zyh.vo.MenuVo;
import com.zyh.vo.RoleMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * @Author zyh
 * @Date 2022/9/19 20:45
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Menu> findMenuList(MenuVo menuVo) {
        List<Menu> menuList = baseMapper.selectList(null);
        List<Menu> menuTree = MenuTree.makeMenuTree(menuList, 0L);
        return menuTree;
    }

    @Override
    public List<Menu> findMenuParentList() {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Menu::getType, Arrays.asList(0,1));
        List<Menu> menuList = baseMapper.selectList(queryWrapper);
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

    @Override
    public RoleMenuVo findMenuTree(Long userId, Long roleId) {
        //查询当前登录用户信息
        User user = userMapper.selectById(userId);
        //创建集合保存权限菜单
        List<Menu> list=null;
        //判断当前登录用户是否是管理员，如果是管理员，则查询所有的权限信息；如果不是管理员，则需要根据当前用户Id查询出当前用户所拥有的权限信息。
        if (user!=null && !ObjectUtils.isEmpty(user.getUserType()) && user.getUserType()==0){
            //查询出所有的权限菜单
            list=baseMapper.selectList(null);
        }else {
            //根据当前用户Id查询出当前用户所拥有的权限信息。
            list=baseMapper.findMenuListByUserId(userId);
        }
        //将登录用户所拥有的权限信息封装成菜单树
        List<Menu> menuList = MenuTree.makeMenuTree(list, 0L);
        //查询要分配角色拥有的权限列表
        List<Menu> roleMenu = baseMapper.findMenuListByRoleId(roleId);
        //创建集合保存权限ID
        List<Long> listIds=new ArrayList<Long>();
        //进行数据回显
        Optional.ofNullable(list).orElse(new ArrayList<>())
                .stream()
                .filter(Objects::nonNull)
                .forEach(item->{
                    Optional.ofNullable(roleMenu).orElse(new ArrayList<>())
                            .stream()
                            .filter(Objects::nonNull)
                            .forEach(obj->{
                                //判断两者的权限ID是否一致，如果一致，则表示拥有该权限
                                if (item.getId().equals(obj.getId())){
                                    //将权限ID保存到集合中
                                    listIds.add(obj.getId());
                                    return;
                                }
                            });
                });
        //创建RoleMenuVo类对象
        RoleMenuVo vo = new RoleMenuVo(menuList,listIds.toArray());
        return vo;
    }

}
