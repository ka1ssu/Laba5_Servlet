package com.example.laba5;

import java.io.*;
import java.sql.*;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/Update")
public class Update extends HttpServlet {
    private final static String URL = "jdbc:mysql://localhost:3306/mydb";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";
    private Connection connection;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        String color = request.getParameter("color");
        String mileageParam = request.getParameter("mileage");
        int mileage = mileageParam != null && !mileageParam.isEmpty() ? Integer.parseInt(mileageParam) : 0;
        String priceParam = request.getParameter("price");
        int price = priceParam != null && !priceParam.isEmpty() ? Integer.parseInt(priceParam) : 0;
        PreparedStatement ps = null;
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
            String UPDATE_QUERY = "UPDATE cars SET brand = ?, model = ?, mileage = ?, color = ?, price = ? WHERE idcars = ?";
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            ps = connection.prepareStatement(UPDATE_QUERY);
            ps.setString(1, brand);
            ps.setString(2, model);
            ps.setInt(3, mileage);
            ps.setString(4, color);
            ps.setInt(5, price);
            ps.setInt(6, lastCarId);
            System.out.println(ps);
            ps.executeUpdate();
            System.out.println("execute is successful");
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
