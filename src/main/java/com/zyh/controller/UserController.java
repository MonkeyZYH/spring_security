package com.zyh.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyh.entity.ErrorCode;
import com.zyh.entity.Result;
import com.zyh.entity.User;
import com.zyh.mapper.UserMapper;
import com.zyh.service.LoginService;
import com.zyh.service.UserService;
import com.zyh.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * @Author zyh
 * @Date 2022/9/18 23:11
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/getMenuList")
    public Result getMenuList(){
       return loginService.getMenuList();
    }
    /**
     * 查询用户列表
     * @param userVo
     * @return
     */
    @GetMapping("/list")
    public Result list(UserVo userVo) {
        //创建分页信息
        IPage<User> page = new Page<User>(userVo.getPageNo(), userVo.getPageSize());
        //调用分页查询方法
        userService.findUserListByPage(page, userVo);
        //返回数据
        return Result.success(page);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody User user){
        //查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,user.getUserName());
        User selectOne = userMapper.selectOne(queryWrapper);
        if (selectOne!=null){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), "该用户名已存在!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userService.save(user)){
            return Result.success("添加成功!");
        }
        return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), "添加失败!");
    }

}
