package com.zyh.utils;

/**
 * @Author zyh
 * @Date 2022/9/18 22:04
 */

import com.zyh.entity.Menu;
import com.zyh.vo.RouterVo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 菜单树工具类
 */
public class MenuTree {

    /**
     * 生成路由
     *
     * @param menuList 菜单列表
     * @param pid      父菜单ID
     * @return
     */
    public static List<RouterVo> makeRouter(List<Menu> menuList, Long pid) {
        //创建集合保存路由信息
        List<RouterVo> routerVoList = new ArrayList<>();
        //判断菜单列表是否为空，如果不为空则使用菜单列表，否则创建集合对象
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                //筛选不为空的菜单及菜单父id相同的数据
                .stream().filter(item -> item != null && Objects.equals(item.getParentId(), pid))
                .forEach(item -> {
                    RouterVo routerVo = new RouterVo();
                    routerVo.setName(item.getName());//路由名称
                    routerVo.setPath(item.getPath());//路由地址
                    //判断是否是一级菜单
                    if (item.getParentId() == 0L) {
                        routerVo.setComponent("Layout");//一级菜单组件
                        routerVo.setAlwaysShow(true);//显示路由
                    } else {
                        routerVo.setComponent(item.getComponent());//具体某一个组件
                        routerVo.setAlwaysShow(false);//折叠路由
                    }
                    //设置Meta信息
                    routerVo.setMeta(routerVo.new Meta(item.getMenuName(), item.getIcon(), item.getPerms().split(",")));
                    //递归生成路由
                    List<RouterVo> children = makeRouter(menuList, item.getId());//子菜单
                    routerVo.setChildren(children);//设置子路由到路由对象中
                    //将路由信息添加到集合中
                    routerVoList.add(routerVo);
                });
        return routerVoList;
    }

    /**
     * 生成菜单树
     *
     * @param menuList
     * @param pid
     * @return
     */
    public static List<Menu> makeMenuTree(List<Menu> menuList, Long pid) {
        //创建集合保存菜单数据
        List<Menu> menuArrayList = new ArrayList<>();
        //判断菜单列表是否为空，如果不为空则使用菜单列表，否则创建集合对象
        Optional.ofNullable(menuList).orElse(new ArrayList<Menu>())
                //筛选不为空的菜单及菜单父id相同的数据
                .stream().filter(item -> item != null && Objects.equals(item.getParentId(), pid))
                .forEach(item -> {
                    //创建权限菜单对象
                    Menu menu = new Menu();
                    //将原有的属性复制给菜单对象
                    BeanUtils.copyProperties(item, menu);
                    //获取每一个item的下级菜单,递归生成菜单树
                    List<Menu> children = makeMenuTree(menuList, item.getId());
                    //设置子菜单
                    menu.setChildren(children);
                    //将菜单对象添加到集合
                    menuArrayList.add(menu);
                });
        return menuArrayList;
    }
}
