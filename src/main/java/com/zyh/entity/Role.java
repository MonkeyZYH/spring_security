package com.zyh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author zyh
 * @Date 2022/9/22 17:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String roleKey;

    private String status;

    private Integer delFlag;

    private Long createBy;

    private Long createUser;

    private LocalDateTime createTime;

    private Long updateBy;

    private LocalDateTime updateTime;

    private String remark;
}
