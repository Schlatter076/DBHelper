package loyer.mysql;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlDemo {
  
  private static File file = null;

  public static void main(String[] args) {
    try {
      //writeToDB();
      readFromDB();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * 获取文件
   * 
   * @param inFile
   * @return
   * @throws FileNotFoundException
   */
  public static FileInputStream getImageByte(String inFile) throws FileNotFoundException {
    FileInputStream imageByte = null;
    file = new File(inFile);
    imageByte = new FileInputStream(file);
    return imageByte;
  }
  /**
   * 连接数据库，存储图片
   */
  public static void writeToDB() {
    try {
      Connection conn = DBHelper.getConnection();
      InputStream in = getImageByte("resource/temp.png");
      String sql = "insert into tb_file(fname,fcontent) values(?,?)";
      PreparedStatement ptmt = conn.prepareStatement(sql);
      ptmt.setString(1, "二狗");
      ptmt.setBinaryStream(2, in, in.available());
      ptmt.execute();

    } catch (SQLException | IOException e) {
      e.printStackTrace();
    } 
  }
  /**
   * 从数据库中读出图片，存放在E盘
   * @throws Exception 
   */
  public static void readFromDB() throws Exception {
    try {
      DBHelper.getConnection();
      String sql = "select * from tb_file where fid='1'";
      ResultSet res = DBHelper.Search(sql, null);
      if (res.next()) {
        InputStream inputStream = res.getBinaryStream("fcontent");

        FileOutputStream fileOutputStream = new FileOutputStream(new File("resource/InputPicture.jpg"));
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
          fileOutputStream.write(buffer, 0, len);
        }
        inputStream.close();
        fileOutputStream.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
}
