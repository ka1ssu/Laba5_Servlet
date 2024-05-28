package com.example.laba5;

import java.io.*;
import java.sql.*;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/Delete")
public class Delete extends HttpServlet {
    private final static String URL = "jdbc:mysql://localhost:3306/mydb";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";
    private Connection connection;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PreparedStatement ps = null;
        String DELETE_QUERY = "DELETE FROM cars WHERE idcars = ?";
        int lastCarId = 0;
        String SELECT_LAST_ID_QUERY = "SELECT idcars FROM cars ORDER BY idcars DESC LIMIT 1";

        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement selectPs = connection.prepareStatement(SELECT_LAST_ID_QUERY);
            ResultSet resultSet = selectPs.executeQuery();

            if (resultSet.next()) {
                lastCarId = resultSet.getInt("idcars");
                System.out.println(lastCarId);
            }
            ps = connection.prepareStatement(DELETE_QUERY);
            ps.setInt(1, lastCarId);
            ps.executeUpdate();
            System.out.println("Delete operation successful");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        response.sendRedirect("/Laba5_war_exploded/");

    }
}