package com.gzu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("用户名: " + username + ", 密码: " + password);

        // 假设验证用户
        if (isValidUser(username, password)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", username);
            session.setMaxInactiveInterval(30 * 60); // 设置会话超时时间
            response.sendRedirect("index.jsp"); // 这里重定向
        }
        else {
            response.sendRedirect("index.jsp?error=true");
        }
    }

    private boolean isValidUser(String username, String password) {
        return "Tok1ame".equals(username) && "123456".equals(password);
    }
}
