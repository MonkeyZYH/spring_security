package com.zyh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zyh.entity.Role;
import com.zyh.vo.RoleVo;
import org.springframework.stereotype.Repository;

/**
 * @Author zyh
 * @Date 2022/9/22 17:50
 */
@Repository
public interface RoleService extends IService<Role> {

    /**
     * 根据用户查询角色列表
     * @param page
     * @param roleVo
     * @return
     */
    IPage<Role> findRoleListByUserId(IPage<Role> page, RoleVo roleVo);

    /**
     * 检查角色是否被使用
     * @param id
     * @return
     */
    boolean hashRoleCount(Long id);

    /**
     * 删除角色
     * @param id
     * @return
     */
    boolean deleteRoleById(Long id);
}
