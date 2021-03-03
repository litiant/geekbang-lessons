package org.geektimes.projects.user.sql;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ServiceLoader;

/**
 * Created by lt 2021/3/3
 */
public class MyDBConnectionManager extends DBConnectionManager{

    private Connection connection;

    public MyDBConnectionManager(){
        initDataSource();
//        try {
////            ServiceLoader<Driver> loader = ServiceLoader.load(Driver.class);
////            loader.iterator().next();
//            Class.forName("org.apache.derby.jdbc.EmbeddedDrive").newInstance();
//            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/user-platform;create=true;user=root;password=root");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
    }

    private void initDataSource(){
        try {
            Context context = new InitialContext();
            Context envContext = (Context) context.lookup("java:comp/env/");
            DataSource dataSource = (DataSource) envContext.lookup("jdbc/UserPlatformDB");
            setConnection(dataSource.getConnection());
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
