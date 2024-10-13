package com.gzu.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取当前会话并使其无效
        HttpSession session = request.getSession(false); // 获取现有会话，不创建新会话
        if (session != null) {
            session.invalidate(); // 使当前会话失效
        }

        // 重定向到首页并传递登出消息
        response.sendRedirect("index.jsp?message=logout");
    }
}
