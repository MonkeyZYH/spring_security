package com.zyh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyh.entity.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author zyh
 * @Date 2022/9/22 17:48
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询角色数量
     * @param id
     * @return
     */
    @Select("select count(1) from sys_user_role where role_id=#{roleId}")
    int getRoleCountByRoleId(Long id);

    /**
     * 删除角色权限关系
     * @param id
     */
    @Delete("delete from sys_role_menu where role_id=#{id}")
    void deleteRoleMenuByRoleId(Long id);

    int saveRoleMenu(Long roleId, List<Long> menuIds);
}
