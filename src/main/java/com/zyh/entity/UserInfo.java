package com.zyh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author zyh
 * @Date 2022/9/18 15:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {
    private Long id;//用户ID
    private String name;//用户名称
    private String avatar;//头像
    private String introduction;//介绍
    private Object[] roles;//角色权限集合
}