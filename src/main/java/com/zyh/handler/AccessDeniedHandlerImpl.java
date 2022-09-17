package com.zyh.handler;

import com.alibaba.fastjson.JSON;
import com.zyh.entity.Result;
import com.zyh.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author zyh
 * @Date 2022/9/17 13:54
 */
//权限错误统一处理返回结果集
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Result result = Result.fail(HttpStatus.FORBIDDEN.value(), "权限不足!");
        String resultJson = JSON.toJSONString(result);
        //处理异常
        WebUtils.renderString(response,resultJson);
    }
}
