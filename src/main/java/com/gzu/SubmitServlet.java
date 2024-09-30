package com.gzu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/submit")
public class SubmitServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置请求和响应的编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // 输入数据测试
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>数据测试</h1>");
        out.println("<form method='POST' action='/SessionTest/submit'>"
                + "<label for='input'>输入数据：</label>"
                + "<input type='text' name='data' id='input' required/>"
                + "<input type='submit' value='提交'/>"
                + "</form>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置请求和响应的编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // 获取输入数据
        String userInput = request.getParameter("data");

        // 防止XSS攻击
        String safeInput = escapeHtml(userInput);

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>你的输入:</h1>");
        out.println("<p>" + escapeHtml(safeInput) + "</p>");
        out.println("</body></html>");
    }

    private String escapeHtml(String input) {
        System.out.println(("我拿到了数据：" + input));
        if (input == null) return null;
        String safeInput = input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
        System.out.println("脚本防御成功：" + safeInput);
        return safeInput;
    }
}
