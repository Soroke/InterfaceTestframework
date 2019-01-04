package net.faxuan.interfaceTest.util;

import java.sql.*;

/**
 * Created by song on 2018/12/25.
 */
public class DatabaseUtil {
    private Connection conn = null;
    PreparedStatement statement = null;

    private String url = null;
    private String userName = null;
    private String passWord = null;

    public DatabaseUtil(String url,String userName,String passWord) {
        this.url = url;
        this.userName=userName;
        this.passWord=passWord;
    }

    /**
     *连接数据库
     */
    public void connSQL() {
        if (url==null || url.equals("") || userName==null || passWord==null){
            System.err.println("数据库连接信息未设置完整！");
            System.err.println("请先设置数据库连接信息！！");
            return;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection( url, userName, passWord);
        }
        catch ( ClassNotFoundException cnfex ) {
            System.err.println("装载 JDBC/ODBC 驱动程序失败。" );
            cnfex.printStackTrace();
        }
        catch ( SQLException sqlex ) {
            System.err.println( "无法连接数据库" );
            sqlex.printStackTrace();
        }
    }

    /**
     *断开数据库连接
     */
    public void deconnSQL() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (conn != null){
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("关闭数据库问题 ：");
            e.printStackTrace();
        }
    }

    /**
     *执行数据库查询语句
     * @param sql SQL语句
     * @return
     */
    public ResultSet selectSQL(String sql) {
        ResultSet rs = null;
        try {
            statement = conn.prepareStatement(sql);
            rs = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    // 执行数据库插入语句
    public boolean insertSQL(String sql) {
        try {
            statement = conn.prepareStatement(sql);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("插入数据库时出错：");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("插入时出错：");
            e.printStackTrace();
        }
        return false;
    }
    //执行数据库删除语句
    boolean deleteSQL(String sql) {
        try {
            statement = conn.prepareStatement(sql);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("插入数据库时出错：");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("插入时出错：");
            e.printStackTrace();
        }
        return false;
    }
    //执行数据库更新语句
    public boolean updateSQL(String sql) {
        try {
            statement = conn.prepareStatement(sql);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("插入数据库时出错：");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("插入时出错：");
            e.printStackTrace();
        }
        return false;
    }



}
