package com.zyh.vo;

import com.zyh.entity.Role;
import lombok.Data;

/**
 * @Author zyh
 * @Date 2022/9/22 17:37
 */
@Data
public class RoleVo extends Role {
    //当前页码
    private Long pageNo = 1L;
    //每页显示数量
    private Long pageSize = 10L;
    //用户ID
    private Long userId;
}
