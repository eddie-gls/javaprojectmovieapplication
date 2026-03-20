
import java.sql.Connection;
import java.sql.DriverManager;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 932562301
 */
public class DataSource {
        static Connection conn = null;
  

  public static Connection createConnection()
  {
  
    try
    {
          // db parameters
        String url       = "jdbc:mysql://localhost:3306/javaproject";
        String user      = "root";
        String password  = "";

        // create a connection to the database
        conn = DriverManager.getConnection(url, user, password);

    }
    catch( Exception e )
    {
      System.out.println("Error Occured " + e.toString());
    }
    return conn;
  }
}
