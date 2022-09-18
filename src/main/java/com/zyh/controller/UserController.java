package com.zyh.controller;

import com.zyh.entity.Result;
import com.zyh.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zyh
 * @Date 2022/9/18 23:11
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private LoginService loginService;
    @GetMapping("/getMenuList")
    public Result getMenuList(){
       return loginService.getMenuList();
    }
}
