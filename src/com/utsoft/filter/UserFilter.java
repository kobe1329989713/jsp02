package com.utsoft.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter 过滤器、登录拦截。
 */
public class UserFilter implements Filter {
    // init
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 注要在这里面执行业务。
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        // 获取session
        HttpSession session = req.getSession();
        String userName = (String) session.getAttribute("username");
        if (userName != null){
            // 继续调用下一个 Filter(拦截器)拦截链,如果不存在就执行原始访问资源。
            chain.doFilter(req,res);
        }else {
            res.sendRedirect("../index.html");
        }
    }
    // 这里面做完了，还要到 web.xml 里面去注册的

    // 消毁
    @Override
    public void destroy() {

    }
}
