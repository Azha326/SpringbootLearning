package com.azha.Utils;

        import com.azha.pojo.Result;

        import java.sql.*;

public class JDBCUtil {
    private static String driver = "com.mysql.jdbc.Driver";
    private static String masterUrl = "jdbc:mysql://192.168.112.128:3306/mydb" ;//主
    private static String slaveUrl = "jdbc:mysql://192.168.112.128:3316/mydb" ;//从
    private static String user = "root" ;
    private static String password = "root";
    static {
        try {
            Class.forName(driver);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public static Connection getConnection(boolean isWrite){
        Connection conn= null;
        String url = isWrite ? masterUrl : slaveUrl;
        try {
            conn=DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static void close(Connection conn, PreparedStatement statement, ResultSet resultSet){
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(statement!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
