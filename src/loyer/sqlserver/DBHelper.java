package loyer.sqlserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * 数据库帮助类
 * @author Loyer
 * @coding utf8
 */
public class DBHelper {
  //数据库连接字符串
  private final static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
  private final static String url = "jdbc:sqlserver://localhost:1433;databaseName=ectestsys";
  private final static String username = "sa";
  private final static String password = "SQLserver0";
  private Connection connection = null;
  private ResultSet res = null;
  /**
   * 提供数据库连接方法
   * @return getConnectionCommon()
   * @throws Exception
   */
  public static Connection getConnection() throws Exception {
    return getConnectionCommon();
  }
  /**
   * 定义数据库连接方法
   * @return connection
   * @throws Exception
   */
  public static Connection getConnectionCommon() throws Exception {
    Connection connection = null;  //连接对象
    try {
      //加载驱动
      Class.forName(driver);
      connection = DriverManager.getConnection(url, username, password);
    } catch(ClassNotFoundException e) {
      System.err.println("JDBC驱动加载失败");
    } catch(SQLException e) {
      System.err.println("无法连接到数据库");
    }
    return connection;
  }
  /**
   * 提供数据库查询方法
   * @param sql
   * @param str
   * @return
   * @throws Exception
   */
  public  ResultSet Search(String sql, String str[]) throws Exception {
    try {
      connection = getConnection();
      PreparedStatement pstmt = connection.prepareStatement(sql);
      if(str != null) {
        for(int i = 0; i < str.length; i++) {
          pstmt.setString(i + 1, str[i]);
        }
      }
      res = pstmt.executeQuery();
    } catch(Exception e) {
      e.printStackTrace();
    }
    return res;
  }
  /**
   * 提供数据库增删修改方法
   * @param sql
   * @param str
   * @return
   * @throws Exception
   */
  public int AddU(String sql, String str[]) throws Exception {
    int getBack = 0;
    try {
      connection = getConnection();
      PreparedStatement pstmt = connection.prepareStatement(sql);
      if(str != null) {
        for(int i = 0; i < str.length; i++) {
          pstmt.setString(i + 1, str[i]);
        }
      }
      getBack = pstmt.executeUpdate();
    } catch(Exception e) {
      e.printStackTrace();
    }
    return getBack;
  }
 /**
  * 数据库关闭连接方法
  * @param res
  * @param stmt
  * @param pstmt
  * @param connection
  */
  public static void close(ResultSet res, PreparedStatement pstmt, Connection connection) {
    if(res != null) {
      try {
        res.close();
      } catch(SQLException e) {
        e.printStackTrace();
      }
    }
    if(pstmt != null) {
      try {
        pstmt.close();
      } catch(SQLException e) {
        e.printStackTrace();
      }
    }
    if(connection != null) {
      try {
        connection.close();
      } catch(SQLException e) {
        e.printStackTrace();
      }
    }
  }
}