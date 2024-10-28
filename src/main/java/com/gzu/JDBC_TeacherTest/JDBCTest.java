// 测试JDBC连接
package com.gzu.JDBC_TeacherTest;

import java.sql.*;

public class JDBCTest {

    static final Integer ID = 2;
    static final String URL = "jdbc:mysql://localhost:3306/JDBCtest?serverTimezone=GMT&characterEncoding=UTF-8";
    static final String USER = "root";
    static final String PASSWORD = "123456789";
    static final String SQL = "SELECT id, name AS student_name, course, birthday FROM teacher WHERE id = ? ";

    public static void main(String[] args) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // 0.注册驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 1. 建立数据库连接 DriverManager的getConnection()
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            // 2. 准备SQL语句
            ps = conn.prepareStatement(SQL);
            ps.setInt(1, ID);
            // 3. 执行SQL语句
            rs = ps.executeQuery();
            // 4. 处理结果集
            while (rs.next()) {
//                System.out.println(rs.getInt("id") + " " + rs.getString("student_name") + " " +
//                        rs.getInt("age") + " " + rs.getString("gender"));
                System.out.println(rs.getObject(1)+" "+rs.getObject(2)+" "+rs.getObject(3)+" "+rs.getObject(4));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
