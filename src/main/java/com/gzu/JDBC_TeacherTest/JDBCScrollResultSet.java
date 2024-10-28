// 可滚动结果集
package com.gzu.JDBC_TeacherTest;

import java.sql.*;

public class JDBCScrollResultSet {

    static final String URL = "jdbc:mysql://localhost:3306/JDBCtest?serverTimezone=GMT&characterEncoding=UTF-8";
    static final String USER = "root";
    static final String PASSWORD = "123456789";
    static final String SQL = "SELECT id, name, course, birthday FROM teacher";

    public static void main(String[] args) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // 注册驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 建立数据库连接
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            // 准备SQL语句，设置可滚动和只读
            ps = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            // 执行SQL语句
            rs = ps.executeQuery();

            // 移动到倒数第二行并输出
            if (rs.last()) { // 移动到最后一行
                System.out.println("倒数第二条数据：");
                rs.absolute(-2); // 移动到倒数第二行
                System.out.println(rs.getInt("id") + " " + rs.getString("name") + " " + rs.getString("course") + " " + rs.getDate("birthday"));
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
