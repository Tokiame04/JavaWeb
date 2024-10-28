// 插入操作
package com.gzu.JDBC_TeacherTest;

import java.sql.*;

public class JDBCCreate {

    static final String NAME = "宇智波止水";
    static final String COURSE = "计算机";
    static final Date BIRTHDAY = Date.valueOf("1995-12-12");
    static final String URL = "jdbc:mysql://localhost:3306/JDBCtest?serverTimezone=GMT&characterEncoding=UTF-8";
    static final String USER = "root";
    static final String PASSWORD = "123456789";
    static final String SQL = "INSERT INTO teacher(name, course, birthday) VALUES(?, ?, ?)";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            conn.setAutoCommit(false);

            // 查询当前最大ID
            String queryMaxIdSQL = "SELECT MAX(id) AS max_id FROM teacher";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(queryMaxIdSQL)) {
                if (rs.next()) {
                    int maxId = rs.getInt("max_id");

                    // 重置AUTO_INCREMENT值
                    String resetAutoIncrementSQL = "ALTER TABLE teacher AUTO_INCREMENT = ?";
                    try (PreparedStatement resetPs = conn.prepareStatement(resetAutoIncrementSQL)) {
                        resetPs.setInt(1, maxId + 1);
                        resetPs.executeUpdate();
                    }
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(SQL)) {
                ps.setString(1, NAME);
                ps.setString(2, COURSE);
                ps.setDate(3, BIRTHDAY);
                ps.executeUpdate();
                conn.commit();
                System.out.println("插入成功");
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
