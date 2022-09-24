package com.zyh;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zyh.config.SecurityConfig;
import com.zyh.entity.Menu;
import com.zyh.entity.User;
import com.zyh.mapper.MenuMapper;
import com.zyh.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class SpringSecurityApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        //生成密文
        System.out.println(passwordEncoder.encode("123456"));//$2a$10$/x2r6bKtxPbbmOrb/CKxe.8DrvlS30FGQzfDSVGbqjs0y5t5y5hkC
        //校验（正确true，错误false）
        System.out.println(passwordEncoder.matches("123456",
                "$2a$10$/x2r6bKtxPbbmOrb/CKxe.8DrvlS30FGQzfDSVGbqjs0y5t5y5hkC"));
    }

    @Test
    void selectPermsByUserId(){
        System.out.println(menuMapper.selectPermsByUserId(1L));
    }
    @Test
    void find1(){
        System.out.println(menuMapper.findMenuListByUserId(1L));
//        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
//        System.out.println(queryWrapper.eq(User::getId, 1));
    }
}
