package com.zyh.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zyh.entity.*;
import com.zyh.service.LoginService;
import com.zyh.utils.JwtUtil;
import com.zyh.utils.MenuTree;
import com.zyh.utils.RedisCache;
import com.zyh.vo.RouterVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
    public Result getInfo() {
        //从Spring Security上下文获取用户信息
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        //判断authentication对象是否为空
        if (authentication == null) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), "用户信息查询失败");
        }
        //获取用户信息
        LoginUser user = (LoginUser) authentication.getPrincipal();
        //获取该用户拥有的权限信息
        List<String> permissions = user.getPermissions();
        //把permissions中String类型的权限信息封装成SimpleGrantedAuthority对象
        for (String permission : permissions) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
        }
        Object[] roles= permissions.toArray();
        //创建用户信息对象
        UserInfo userInfo = new UserInfo(user.getUser().getId(), user.getUser().getNickName(),
                user.getUser().getAvatar(), null, roles);
        //返回数据
        return Result.success(userInfo);
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

    @Override
    public Result getMenuList() {
        //从Spring Security上下文获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取用户信息
        LoginUser user = (LoginUser) authentication.getPrincipal();
        //获取该用户拥有的权限信息
        List<String> permissions = user.getPermissions();
        List<String> collect = permissions.stream().filter(item -> item != null)
                .collect(Collectors.toList());
//        String join = StringUtils.join(collect, ",");
        String parse = (String) JSON.parse(String.valueOf(collect));
        List<Menu> menus = JSON.parseArray(parse, Menu.class);
        //生成路由数据
        List<RouterVo> menuList = MenuTree.makeRouter(menus, 0L);
        return Result.success(menuList);
    }
}
