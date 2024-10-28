// 批量添加
package com.gzu.JDBC_TeacherTest;

import java.sql.*;

public class JDBCBatchAdd {

    static final String URL = "jdbc:mysql://localhost:3306/JDBCtest?serverTimezone=GMT&characterEncoding=UTF-8";
    static final String USER = "root";
    static final String PASSWORD = "123456789";
    static final String SQL = "INSERT INTO teacher(name, course, birthday) VALUES(?, ?, ?)";

    public static void main(String[] args) {
        try {
            // 注册驱动程序
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

                for (int i = 1; i <= 500; i++) {
                    ps.setString(1, "教师" + i);
                    ps.setString(2, "课程" + (i % 10 + 1)); // 课程名称变化
                    ps.setDate(3, Date.valueOf(Date.valueOf("1980-01-01").toLocalDate().plusDays(i % 365))); // 模拟生日

                    ps.addBatch();

                    // 每插入100条数据执行并清空批处理
                    if (i % 100 == 0) {
                        ps.executeBatch();
                        conn.commit();
                        ps.clearBatch();
                    }
                }

                // 执行剩余未提交的数据
                ps.executeBatch();
                conn.commit();
                System.out.println("完成500条教师数据的批量插入");

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
