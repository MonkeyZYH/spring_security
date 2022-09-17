package com.zyh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyh.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author zyh
 * @Date 2022/9/14 23:39
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
