package com.example.laba5;

import java.io.*;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "CarsServlet", value = "/CarsServlet")
public class CarsServlet extends HttpServlet {
    private final static String URL = "jdbc:mysql://localhost:3306/mydb";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";
    private Connection connection;

    public void init() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        int mileage = Integer.parseInt(request.getParameter("mileage"));
        String color = request.getParameter("color");
        int price = Integer.parseInt(request.getParameter("price"));

        PreparedStatement ps = null;
        String INSERT_NEW = "INSERT INTO cars (brand, model, mileage, color, price) VALUES (?, ?, ?, ?, ?)";

        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            ps = connection.prepareStatement(INSERT_NEW);
            ps.setString(1, brand);
            ps.setString(2, model);
            ps.setInt(3, mileage);
            ps.setString(4, color);
            ps.setInt(5, price);
            System.out.println(ps);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/Laba5_war_exploded/");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WorkerDB worker = new WorkerDB();
        String query = "Select * from cars";
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>Таблица</title><link href=\"css/bootstrap.min.css\" rel=\"stylesheet\"></head><body><div><table class=\"table\"><thead><tr><th scope=\"col\">brand</th><th scope=\"col\">model</th><th scope=\"col\">mileage</th> <th scope=\"col\">color</th><th scope=\"col\">price</th></tr></thead>");
        try{
            Statement statement = worker.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                out.println("<tbody> <tr><td>" + resultSet.getString("brand") + "</td><td>" + resultSet.getString("model") + "</td><td>" + resultSet.getInt("mileage") + "</td><td>" + resultSet.getString("color") + "</td><td>" + resultSet.getInt("price") + "</td>");
                Cars car = new Cars(resultSet.getString("brand"), resultSet.getString("model"), resultSet.getInt("mileage"), resultSet.getString("color"), resultSet.getInt("price"));
                System.out.println(car.toString());
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        out.println("</tbody></table></div ><script src =\"js/bootstrap.bundle.min.js \"></script ></body ></html >");
    }

    public void destroy() {
    }
}