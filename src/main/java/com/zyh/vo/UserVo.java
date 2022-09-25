package com.zyh.vo;

import com.zyh.entity.User;
import lombok.Data;

/**
 * @Author zyh
 * @Date 2022/9/25 10:49
 */
@Data
public class UserVo extends User {
    //当前页码
    private Long pageNo = 1L;
    //每页显示数量
    private Long pageSize = 10L;
}
