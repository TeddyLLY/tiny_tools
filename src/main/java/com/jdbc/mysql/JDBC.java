package com.jdbc.mysql;

import java.sql.*;

public class JDBC {
    public static void main(String[] args) {

        // Connection Info
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/main";
        String username = "root";
        String password = "!QAZ2wsx";

        // INSERT SQL
        // 需要 insert insertFlag 改成true
        Boolean insertFlag = true;
        String insertSQLBeforeSelect =
                "INSERT INTO employee (first_name, last_name, email, hire_date, salary, department)" +
                        "VALUES ('slaver', 'ss', 'johndoe@example.com', '2025-09-11', 770000.00, 'RD');";

        // SELECT SQL
        String selectSQL = "select * from employee;";



        //Code Part
        try {
            //connect
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);


            //insert
            int insertRow = 0;
            if(insertFlag){
                // Disable auto-commit mode
                connection.setAutoCommit(false);
                //insert
                PreparedStatement ps = connection.prepareStatement(insertSQLBeforeSelect);
                int rowsAffected = ps.executeUpdate();
                insertRow ++ ;

                //print rows
                System.out.println( "\n*******\n" + insertRow + " Row(s) Inserted.\n*******");

                //close
                ps.close();
                connection.commit();
            }

            //select
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(selectSQL);
            int totalCount = 0 ;

            //print info
            while (rs.next()) {
                String rsString = "";
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    rsString += rs.getMetaData().getColumnName(i) + ":" + rs.getString(i) + " , " ;
                }
                System.out.println(rsString); // Move to the next line for the next row

                totalCount ++ ;
            }
            System.out.println( "*******\nSelect Total Count = " + totalCount + "\n*******");

            //close
            rs.close();
            st.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
