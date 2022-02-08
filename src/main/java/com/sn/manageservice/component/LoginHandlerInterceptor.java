package com.sn.manageservice.component;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 向南
 * @date 2021/12/26 18:27
 * @description
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object obj = request.getSession().getAttribute("userName");
        if(obj == null){
            //没有登录，设置错误信息并转发到登录页面
            request.setAttribute("msg","没有权限请先登陆");
            request.getRequestDispatcher("/manage/login")
                    .forward(request,response);
            return false;
        }else{
            //已登录，放行
            return true;
        }
    }
}
