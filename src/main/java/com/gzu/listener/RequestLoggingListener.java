package com.gzu.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

@WebListener
public class RequestLoggingListener implements ServletRequestListener {

    private static final Logger logger = Logger.getLogger(RequestLoggingListener.class.getName());

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        LocalDateTime startTime = LocalDateTime.now();
        request.setAttribute("startTime", startTime); // 将开始时间保存到请求属性中

        String clientIp = request.getRemoteAddr();
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();
        String userAgent = request.getHeader("User-Agent");

        // 打印请求的详细信息到日志
        logger.info("请求开始: ");
        logger.info("时间: " + startTime);
        logger.info("客户端 IP: " + clientIp);
        logger.info("请求方法: " + method);
        logger.info("请求 URI: " + requestURI);
        if (queryString != null) {
            logger.info("查询字符串: " + queryString);
        }
        logger.info("User-Agent: " + userAgent);
        // 打印请求的详细信息到控制台
        System.out.println("请求开始: ");
        System.out.println("时间: " + startTime);
        System.out.println("客户端 IP: " + clientIp);
        System.out.println("请求方法: " + method);
        System.out.println("请求 URI: " + requestURI);
        if (queryString != null) {
            System.out.println("查询字符串: " + queryString);
        }
        System.out.println("User-Agent: " + userAgent);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        LocalDateTime startTime = (LocalDateTime) request.getAttribute("startTime"); // 获取开始时间
        LocalDateTime endTime = LocalDateTime.now();
        long processingTime = ChronoUnit.MILLIS.between(startTime, endTime); // 计算请求处理时间

        // 记录请求处理时间
        logger.info("请求结束: ");
        logger.info("处理时间: " + processingTime + " 毫秒");
        // 打印请求处理时间到控制台
        System.out.println("请求结束: ");
        System.out.println("处理时间: " + processingTime + " 毫秒");
    }
}

