package com.jdbc.oracle;

import java.sql.*;

public class JDBC {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:oracle:thin:@//localhost:1521/FREEPDB1";
        String username = "apuser";
        String password = "1qaz@WSX";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Create a PreparedStatement
            String sql = "SELECT * FROM EMPLOYEE WHERE FIRST_NAME = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set parameters
            preparedStatement.setString(1, "Teddy");

            // Execute the query
            ResultSet rs = preparedStatement.executeQuery();

            // Process the results
            while (rs.next()) {
                String rsString = "";
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    rsString += rs.getMetaData().getColumnName(i) + ":" + rs.getString(i) + " , " ;
                }
                System.out.println(rsString); // Move to the next line for the next row
            }

            // Close resources
            rs.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
