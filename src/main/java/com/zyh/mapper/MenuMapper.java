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

    List<Menu> findMenuListByUserId(Long id);
}
