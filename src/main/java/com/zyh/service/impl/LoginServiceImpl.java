package com.zyh.service.impl;

import com.zyh.entity.LoginUser;
import com.zyh.entity.Result;
import com.zyh.entity.User;
import com.zyh.service.LoginService;
import com.zyh.utils.JwtUtil;
import com.zyh.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.HashMap;

/**
 * @Author zyh
 * @Date 2022/9/15 9:39
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Result login(User user) {
        //AuthenticationManager authenticate进行用户认证

        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果认证没通过，给出提示
        if (ObjectUtils.isEmpty(authenticate)){
            throw new RuntimeException("登录失败");
        }
        //通过，使用工具类生成jwt(存用户信息)
        LoginUser loginUser =(LoginUser) authenticate.getPrincipal();
        String userid = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userid);
        //将token存入redis
        redisCache.setCacheObject("login:"+userid,loginUser);
        HashMap<String, String> map = new HashMap<>();
        map.put("token",jwt);
        return Result.success(map);
    }

    @Override
    public Result logout() {
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser =(LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();
        //删除redis中的值
        redisCache.deleteObject("login:"+userid);
        return Result.success("注销成功!");
    }
}
