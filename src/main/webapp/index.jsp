<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // 获取会话中的用户信息
    String user = (String) session.getAttribute("user");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录和表单</title>
    <style>
        /* 样式代码 */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 400px;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #333;
            text-align: center;
        }
        form {
            margin: 20px 0;
        }
        label {
            display: block;
            margin: 10px 0 5px;
            color: #555;
        }
        input[type="text"], input[type="password"], input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        input[type="submit"] {
            background: #5cb85c;
            color: white;
            border: none;
            cursor: pointer;
            transition: background 0.3s ease;
        }
        input[type="submit"]:hover {
            background: #4cae4c;
        }
        .error {
            color: red;
            text-align: center;
            margin: 10px 0;
        }
        .welcome-message {
            margin: 20px 0;
            padding: 10px;
            background: #e7f3fe;
            border-left: 6px solid #2196F3;
            color: #31708f;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>登录</h1>
    <form method="POST" action="login">
        <label for="username">用户名：</label>
        <input type="text" name="username" id="username" required/>
        <label for="password">密码：</label>
        <input type="password" name="password" id="password" required/>
        <input type="submit" value="登录"/>
    </form>

    <%
        // 检查是否有错误信息
        if (request.getParameter("error") != null) {
    %>
    <p class="error">登录失败，请重试。</p>
    <%
        }
        // 如果用户已登录，显示欢迎信息
        if (user != null) {
    %>
    <div class="welcome-message">
        <h1>欢迎，<%= user %>!</h1>
        <form method="GET" action="form">
            <input type="submit" value="验证CSRF令牌"/>
        </form>
        <form method="GET" action="submit">
            <input type="submit" value="验证XSS防御"/>
        </form>
    </div>
    <%
        }
    %>
</div>
</body>
</html>
