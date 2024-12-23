package org.example;
/*
    Разработать программу регистрации пользователей на основе
    эталона биометрического пароля пользователей, предварительно
    сформировав вектор биометрических параметров, с использованием
    предоставленных образцов клавиатурного почерка. Удалить
    зарегистрированных пользователей, в парольной фразе которых содержится
    символы «asdfg»
     */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        /*
        Properties properties = new Properties();
        try (InputStream input =  new FileInputStream("src/main/resources/sql.properties")){
            properties.load(input);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Connection connection = DriverManager.getConnection(properties.getProperty("database.url"),properties.getProperty("login"),properties.getProperty("password"));
        //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users","root","WARface2017");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS.USERS");

        //int rows = statement.executeUpdate("INSERT Products(ProductName, Price) VALUES ('iPhone X', 76000)," +
        //        "('Galaxy S9', 45000), ('Nokia 9', 36000)");
        //int rows = statement.executeUpdate("DELETE FROM Products WHERE Id = 3");
       // System.out.printf("%d row(s) deleted", rows);

        while (resultSet.next()){
            System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
        }

        connection.close();
         */
        //запуск приложения
        UserManager userManager = new UserManager();
        userManager.start();
    }
}
