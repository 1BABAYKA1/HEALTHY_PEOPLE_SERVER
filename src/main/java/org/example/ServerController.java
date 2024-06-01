package org.example;


import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

@RestController
public class ServerController {

    ArrayList<Object> List_for_trans = new ArrayList<>();

    @RequestMapping(
            value = "/something",
            method = RequestMethod.POST,
            produces = "application/json"
    )
    public @ResponseBody ModelMap uomMatrixSaveOrEdit(
            ModelMap model,
            @RequestParam("parentId") String parentId
    ) {
        model.addAttribute("attributeValues", parentId);
        return model;
    }

    @GetMapping("/get")
    public String hello() {
        return "GET-method working";
    }

    @GetMapping("/post")
    public String answer() {
        return "text";
    }

    @PostMapping("/receive")
    public String receiveMessage(@RequestBody String message) {
        System.out.println("Received message: " + message);
        return "POST-method working";
    }

    @PostMapping("/registr")
    public String receiveMessage_registr(@RequestBody String message) {
        System.out.println("Registration: " + message);

        String[] userData = message.split(" ");
        String fio = userData[0];
        String email = userData[1];
        String phone = userData[2];
        String password1 = userData[3];

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("database connect error");
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/mydb";
        String username = "root";
        String password2 = "toor";

        try (Connection connection = DriverManager.getConnection(url, username, password2)) {
            String insertQuery = "INSERT INTO users (fio, email, phone_number, password) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, fio);
            insertStatement.setString(2, email);
            insertStatement.setString(3, phone);
            insertStatement.setString(4, password1);
            insertStatement.executeUpdate();

            String insertQueryData = "INSERT INTO usersdata (fio) VALUES (?)";
            PreparedStatement insertStatementData = connection.prepareStatement(insertQueryData);
            insertStatementData.setString(1, fio);
            insertStatement.executeUpdate();
            return "Registration successful";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/auth")
    public boolean receiveMessage_auth(@RequestBody String message) {
        System.out.println("Authorization: " + message);

        String[] credentials = message.split(" ");
        String email = credentials[0];
        String password = credentials[1];

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("database connect error");
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/mydb";
        String username = "root";
        String password1 = "toor";

        try (Connection connection = DriverManager.getConnection(url, username, password1)) {
            String selectQuery = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setString(1, email);
            selectStatement.setString(2, password);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Authorization successful");
                return true;
            } else {
                System.out.println("Authorization failed");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/profile")
    public ArrayList<Object> receiveMessage_profile(@RequestBody String message) {
        System.out.println("Authorization: " + message);
        String[] credentials = message.split(" ");
        String email = credentials[0];

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("database connect error");
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/mydb";
        String username = "root";
        String password1 = "toor";

        try (Connection connection = DriverManager.getConnection(url, username, password1)) {
            String selectQuery = "SELECT fio, phone_number FROM users WHERE email = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setString(1, email);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("fio");
                String phone = resultSet.getString("phone_number");
                System.out.println("Profile get successful");
                List_for_trans.add(name);
                List_for_trans.add(email);
                List_for_trans.add(phone);
                return List_for_trans;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return List_for_trans;
    }




    @PostMapping("/uploadImage")
    public String uploadImage(@RequestParam("image") MultipartFile image) {
        try {
            byte[] imageData = image.getBytes();
            // Сохранение изображения в базу данных
            // Добавьте код для сохранения изображения в таблицу базы данных
            return "Image uploaded successfully"; }
        catch (IOException e) {
            return "Error uploading image";
        }
    }
}