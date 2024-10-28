// 删除操作
package com.gzu.JDBC_TeacherTest;

import java.sql.*;

public class JDBCDelete {

    static final Integer ID = 11;
    static final String URL = "jdbc:mysql://localhost:3306/JDBCtest?serverTimezone=GMT&characterEncoding=UTF-8";
    static final String USER = "root";
    static final String PASSWORD = "123456789";
    static final String SQL = "DELETE FROM teacher WHERE id = ?";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(SQL)) {
                ps.setInt(1, ID);
                ps.executeUpdate();
                conn.commit();
                System.out.println("删除成功");
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException(e);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
