// 查询操作
package com.gzu.JDBC_TeacherTest;

import java.sql.*;

public class JDBCRetrieve {

    static final Integer ID = 11;
    static final String URL = "jdbc:mysql://localhost:3306/JDBCtest?serverTimezone=GMT&characterEncoding=UTF-8";
    static final String USER = "root";
    static final String PASSWORD = "123456789";
    static final String SQL = "SELECT id, name, course, birthday FROM teacher WHERE id = ?";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement ps = conn.prepareStatement(SQL)) {
                ps.setInt(1, ID);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        System.out.println("ID: " + rs.getInt("id") +
                                ", Name: " + rs.getString("name") +
                                ", Course: " + rs.getString("course") +
                                ", Birthday: " + rs.getDate("birthday"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
