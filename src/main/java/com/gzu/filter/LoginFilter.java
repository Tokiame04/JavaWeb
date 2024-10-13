package com.gzu.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter(filterName = "LoginFilter", urlPatterns = "/*") // 过滤所有路径
public class LoginFilter implements Filter {

    // 不需要登录的路径列表
    private static final List<String> EXCLUDED_PATHS = Arrays.asList( "/index.jsp", "/login", "/login.jsp", "/register", "/public");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("LoginFilter 初始化");
    }

    @Override
    public void doFilter(javax.servlet.ServletRequest request, javax.servlet.ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 检查当前请求路径是否在排除列表中
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length()).toLowerCase();//使用了 substring() 方法来去除 URI 中的上下文路径部分
        boolean isExcludedPath = EXCLUDED_PATHS.stream().anyMatch(extension -> path.endsWith(extension));

        // 日志记录请求的路径
        System.out.println("请求路径: " + path);

        // 如果访问的路径在排除列表中，直接放行
        if (isExcludedPath) {
            System.out.println("访问的路径无需登录: " + path);
            chain.doFilter(request, response);
            return;
        }

        // 检查用户 session 中是否存在用户信息
        HttpSession session = httpRequest.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            System.out.println("用户已登录: " + session.getAttribute("user"));
            // 用户已登录，继续处理请求
            chain.doFilter(request, response);
        } else {
            System.out.println("用户未登录，重定向到登录页面");
            // 用户未登录，重定向到登录页面
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
        }
    }

    @Override
    public void destroy() {
        System.out.println("LoginFilter 销毁");
    }
}
