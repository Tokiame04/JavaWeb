// 批量删除
package com.gzu.JDBC_TeacherTest;

import java.sql.*;

public class JDBCBatchDelete {

    static final String URL = "jdbc:mysql://localhost:3306/JDBCtest?serverTimezone=GMT&characterEncoding=UTF-8";
    static final String USER = "root";
    static final String PASSWORD = "123456789";
    static final String SQL = "DELETE FROM teacher WHERE id = ?";

    public static void main(String[] args) {
        try {
            // 注册驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(SQL)) {

                for (int i = 11; i <= 510; i++) {
                    ps.setInt(1, i);
                    ps.addBatch();

                    // 每删除100条数据执行并清空批处理
                    if (i % 100 == 0) {
                        ps.executeBatch();
                        conn.commit();
                        ps.clearBatch();
                    }
                }

                // 执行剩余未提交的数据
                ps.executeBatch();
                conn.commit();
                System.out.println("完成500条教师数据的批量删除");

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
