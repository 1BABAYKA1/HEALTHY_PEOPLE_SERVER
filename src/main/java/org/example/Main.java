package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(1);
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/mydb";
        String username = "root";
        String password = "toor";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            String selectQuery = "SELECT * FROM users";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = selectStatement.executeQuery();


            while (resultSet.next()) {
                String fio = resultSet.getString("fio");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone_number");
                String passworduser = resultSet.getString("password");

                System.out.println("FIO: " + fio + ", EMAIL: " + email + ", PHONE: " + phone + ",  PASSWORD: " + passworduser);
            }

            String selectQueryData = "SELECT * FROM usersdata";
            PreparedStatement selectStatementData = connection.prepareStatement(selectQueryData);
            ResultSet resultSetData = selectStatementData.executeQuery();
            System.out.println(resultSetData);



            while (resultSetData.next()) {
                String user = resultSet.getString("User");
                String about = resultSet.getString("About");
                String image = resultSet.getString("Image");

                System.out.println("User: " + user + ", About: " + about + ", Image" + image);
            }

        } catch (SQLException e) {
            System.out.println(2);
            e.printStackTrace();
        }
    }
}