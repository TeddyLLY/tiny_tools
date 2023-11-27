package com.tools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author teddylai
 */
public class OracleJDBC
{
    private static final Logger logger = LogManager.getLogger(OracleJDBC.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
    private static final String currentDirectory = System.getProperty("user.dir");
    private static final String configPath = currentDirectory + "/oracle_config.properties" ;

    public static void main( String[] args ) {
        logger.info("\ntest start !!");

        // write log with time
        logger.info("begin time : " + LocalDateTime.now().format(formatter));
        logger.info("------------------------------\n");

        try {
            // read config file
            // 读取所有行到列表
            FileInputStream fileInputStream = new FileInputStream(configPath);
            Properties properties = new Properties();
            properties.load(fileInputStream);

            String db_uri = (String) properties.get("db.uri");
            String db_username = (String) properties.get("db.username");
            String db_password = (String) properties.get("db.password");
            String jdbc_sql_command = (String) properties.get("jdbc.sql.command");
            Integer threads_count = Integer.parseInt((String) properties.get("threads.count"));

            // run multi thread with config file sql (jdbc)
            ExecutorService executorService = Executors.newFixedThreadPool(threads_count);
            for (int i = 0; i < threads_count; i++) {
                executorService.submit(() -> sqlTask(db_uri, db_username, db_password, jdbc_sql_command));
            }

            // 关闭线程池
            executorService.shutdown();

        }catch (Exception e){
            logger.error("exception happened : {}" , e);
        }

    }



    private static void sqlTask(String db_uri, String db_username, String db_password, String jdbc_sql_command) {
        try {
            // get connection
            Connection connection = DriverManager.getConnection(db_uri, db_username, db_password);
            PreparedStatement preparedStatement = connection.prepareStatement(jdbc_sql_command);
            ResultSet rs = preparedStatement.executeQuery();

            // Close resources
            rs.close();
            preparedStatement.close();
            connection.close();

            // log part
            logger.info("Finish task !! " + LocalDateTime.now().format(formatter));
        }catch (Exception e){
            logger.error("connection error : {}" , e);
        }
    }
}
