package com.zyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyh.entity.Menu;
import com.zyh.vo.MenuVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author zyh
 * @Date 2022/9/19 20:44
 */
@Repository
public interface MenuService extends IService<Menu> {
    /**
     * 查询菜单列表
     * @param menuVo
     * @return
     */
    List<Menu> findMenuList(MenuVo menuVo);

    /**
     * 查询上级菜单列表
     * @return
     */
    List<Menu> findMenuParentList();
}
