package com.zyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyh.entity.User;
import com.zyh.mapper.UserMapper;
import com.zyh.service.UserService;
import com.zyh.vo.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @Author zyh
 * @Date 2022/9/25 10:56
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public IPage<User> findUserListByPage(IPage<User> page, UserVo userVo) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //用户名
        queryWrapper.like(!ObjectUtils.isEmpty(userVo.getUserName()),
                "user_name",userVo.getUserName());
        //真实姓名
        queryWrapper.like(!ObjectUtils.isEmpty(userVo.getNickName()),
                "nick_name",userVo.getNickName());
        //电话
        queryWrapper.like(!ObjectUtils.isEmpty(userVo.getPhonenumber()),
                "phonenumber",userVo.getPhonenumber());
        return baseMapper.selectPage(page,queryWrapper);
    }
}
