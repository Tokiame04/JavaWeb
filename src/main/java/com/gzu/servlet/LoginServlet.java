package com.gzu.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 简单验证用户名和密码
        if ("admin".equals(username) && "123456".equals(password)) {
            // 登录成功，创建 session
            HttpSession session = request.getSession();
            session.setAttribute("user", username);
            response.sendRedirect("welcome.jsp");
        } else {
            // 登录失败，返回登录页面并提示错误信息
            response.sendRedirect("login.jsp?error=1");
        }
    }
}
