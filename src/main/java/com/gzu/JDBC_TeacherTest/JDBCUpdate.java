// 更新操作
package com.gzu.JDBC_TeacherTest;

import java.sql.*;

public class JDBCUpdate {

    static final Integer ID = 11;
    static final String NAME = "宇智波带土";
    static final String URL = "jdbc:mysql://localhost:3306/JDBCtest?serverTimezone=GMT&characterEncoding=UTF-8";
    static final String USER = "root";
    static final String PASSWORD = "123456789";
    static final String SQL = "UPDATE teacher SET name = ? WHERE id = ?";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(SQL)) {
                ps.setString(1, NAME);
                ps.setInt(2, ID);
                ps.executeUpdate();
                conn.commit();
                System.out.println("更新成功");
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
