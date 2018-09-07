package loyer.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHelper {

  private static final String URL = "jdbc:mysql://localhost:3306/uploadpic?useSSL=false&serverTimezone=UTC";
  private static final String USER = "root";
  private static final String PWD = "123456";
  private static PreparedStatement ptmt = null;
  private static Connection conn = null;
  private static ResultSet rs = null;

  /**
   * 连接到数据库
   * 
   * @throws ClassNotFoundException
   * @throws SQLException
   */
  public static Connection getConnection() {
    Connection connection = null; // 连接对象
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection = DriverManager.getConnection(URL, USER, PWD);
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
    return connection;
  }

  /**
   * 提供数据库查询方法
   * 
   * @param sql
   * @param str
   * @return
   * @throws Exception
   */
  public static ResultSet Search(String sql, String str[]) throws Exception {
    try {
      conn = getConnection();
      ptmt = conn.prepareStatement(sql);
      if (str != null) {
        for (int i = 0; i < str.length; i++) {
          ptmt.setString(i + 1, str[i]);
        }
      }
      rs = ptmt.executeQuery();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return rs;
  }

  /**
   * 提供数据库增删修改方法
   * 
   * @param sql
   * @param str
   * @return
   * @throws Exception
   */
  public static int AddU(String sql, String str[]) throws Exception {
    int getBack = 0;
    try {
      conn = getConnection();
      ptmt = conn.prepareStatement(sql);
      if (str != null) {
        for (int i = 0; i < str.length; i++) {
          ptmt.setString(i + 1, str[i]);
        }
      }
      getBack = ptmt.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return getBack;
  }

  /**
   * 数据库关闭连接方法
   * 
   * @param res
   * @param pstmt
   * @param connection
   */
  public static void close(ResultSet res, PreparedStatement pstmt, Connection connection) {
    if (res != null) {
      try {
        res.close();
        res = null;
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    if (pstmt != null) {
      try {
        pstmt.close();
        pstmt = null;
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    if (connection != null) {
      try {
        connection.close();
        connection = null;
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
