package com.zyh.handler;

import com.alibaba.fastjson.JSON;
import com.zyh.entity.Result;
import com.zyh.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author zyh
 * @Date 2022/9/17 13:33
 */
//认证失败统一处理返回结果集
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result result = Result.fail(HttpStatus.UNAUTHORIZED.value(), "用户认证失败!");
        String resultJson = JSON.toJSONString(result);
        //处理异常
        WebUtils.renderString(response,resultJson);

    }
}
