package com.zyh.service;

import com.zyh.entity.Result;
import com.zyh.entity.User;

/**
 * @Author zyh
 * @Date 2022/9/15 9:39
 */
public interface LoginService {
    Result login(User user);

    Result logout();
}
