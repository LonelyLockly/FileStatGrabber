/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evp.db;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import java.sql.Connection;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Егор
 */
public class ConnectionPool
{

    private static final Logger logger = LogManager.getRootLogger();
    private String DB_URL = "jdbc:mysql://localhost:3306/file_stats?characterEncoding=cp1251";
    private String DB_USERNAME = "root";
    private String DB_PASS = "root";

    private BoneCP connectionPool;

    public ConnectionPool()
    {
        init();

        try
        {
            Class.forName("com.mysql.jdbc.Driver"); // load the DB driver
        } catch (ClassNotFoundException se)
        {
            logger.error("Can`t find MySQL driver!");
        }

        BoneCPConfig config = getConfig();
        try
        {
            connectionPool = new BoneCP(config);
        } catch (SQLException ex)
        {
            logger.error("Can`t initialize connection pool!");
        }
    }

    private BoneCPConfig getConfig()
    {
        BoneCPConfig config = new BoneCPConfig();   // create a new configuration object
        config.setJdbcUrl(DB_URL);                  // set the JDBC url
        config.setUsername(DB_USERNAME);            // set the username
        config.setPassword(DB_PASS);                // set the password
        return config;
    }

    private void init()
    {
        Properties db_prop = new Properties();
        try
        {
            db_prop.load(new FileInputStream("./help/db.properties"));
        } catch (IOException e)
        {
            logger.error("Can`t read database configuration file!");
        }
        if (!db_prop.isEmpty())
        {
            DB_URL = db_prop.getProperty("url");
            DB_USERNAME = db_prop.getProperty("username");
            DB_PASS = db_prop.getProperty("pass");
        }
    }

    /**
     * 
     * @return DB connection
     * @throws SQLException 
     */
    public Connection getConnection() throws SQLException
    {
        return connectionPool.getConnection();
    }
}
