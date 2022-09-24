package com.zyh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyh.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author zyh
 * @Date 2022/9/17 11:11
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long id);

    /**
     * 根据用户ID查询权限菜单列表
     * @param id
     * @return
     */
    List<Menu> findMenuListByUserId(Long id);

    /**
     * 根据角色ID查询权限列表
     * @param roleId
     * @return
     */
    List<Menu> findMenuListByRoleId(Long roleId);
}
