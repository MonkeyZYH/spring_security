package com.zyh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zyh.entity.User;
import com.zyh.vo.UserVo;
import org.springframework.stereotype.Repository;

/**
 * @Author zyh
 * @Date 2022/9/25 10:55
 */
@Repository
public interface UserService extends IService<User> {

    /**
     * 分页查询用户信息
     * @param page
     * @param userQueryVo
     * @return
     */
    IPage<User> findUserListByPage(IPage<User> page, UserVo userVo);
}
