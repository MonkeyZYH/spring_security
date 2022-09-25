package com.zyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyh.entity.Role;
import com.zyh.entity.User;
import com.zyh.mapper.RoleMapper;
import com.zyh.mapper.UserMapper;
import com.zyh.service.RoleService;
import com.zyh.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @Author zyh
 * @Date 2022/9/22 17:50
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public IPage<Role> findRoleListByUserId(IPage<Role> page, RoleVo roleVo) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!ObjectUtils.isEmpty(roleVo.getName()),Role::getName,roleVo.getName());
        queryWrapper.orderByAsc(Role::getId);
        User user = userMapper.selectById(roleVo.getUserId());
        if (user!=null && user.getUserType()!=0){
            queryWrapper.eq(Role::getCreateUser,roleVo.getUserId());
        }
        return baseMapper.selectPage(page,queryWrapper);
    }

    @Override
    public boolean hashRoleCount(Long id) {
        return baseMapper.getRoleCountByRoleId(id)>0;
    }

    @Override
    public boolean deleteRoleById(Long id) {
        //删除角色权限关系
        baseMapper.deleteRoleMenuByRoleId(id);
        //删除角色
        return baseMapper.deleteById(id)>0;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean saveRoleMenu(Long roleId, List<Long> menuIds) {
        //删除角色权限关系
        baseMapper.deleteRoleMenuByRoleId(roleId);
        //保存角色权限关系
        return baseMapper.saveRoleMenu(roleId,menuIds)>0;
    }
}
