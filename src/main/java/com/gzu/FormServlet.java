package com.gzu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/form")
public class FormServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置请求和响应的编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // 设置固定的 CSRF 令牌为 "111" 用于测试
        String csrfToken = "111";
        HttpSession session = request.getSession();
        session.setAttribute("csrfToken", csrfToken);

        // 输入数据测试
        response.getWriter().println("<form method='POST' action='/SessionTest/form'>"
                + "<label for='input'>输入数据：</label>"
                + "<input type='text' name='data'/>"
                + "<input type='submit' value='提交'/>"
                + "</form>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置请求和响应的编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = request.getSession();
        String sessionToken = (String) session.getAttribute("csrfToken");

        // 获取输入数据
        String data = request.getParameter("data");

        if (sessionToken != null && sessionToken.equals(data)) {
            response.getWriter().println("提交的数据是：" + data + "，有效的CSRF令牌");
            System.out.println("提交的数据是：" + data + "，有效的CSRF令牌");
        } else {
            response.getWriter().println(data + "无效的CSRF令牌。");
            System.out.println(data + "无效的CSRF令牌。");
        }
    }
}
