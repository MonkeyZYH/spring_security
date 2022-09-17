package com.zyh.controller;

import com.zyh.entity.Result;
import com.zyh.entity.User;
import com.zyh.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author zyh
 * @Date 2022/9/15 9:35
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        //登录
        return loginService.login(user);
    }

    @GetMapping("/logout")
    public Result logout(){
        return loginService.logout();
    }
}

