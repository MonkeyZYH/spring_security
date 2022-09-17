package com.zyh.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zyh
 * @Date 2022/9/14 21:32
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    //hasAuthority单个权限
    //hasAnyAuthority多个权限
    
    @PreAuthorize("hasAuthority('system:test:list')")
    public String test(){
        return "test";
    }
}
