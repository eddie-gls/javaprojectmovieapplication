
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
        // Shared connection reference used by createConnection().
        static Connection conn = null;
  

  public static Connection createConnection()
  {
  
    try
    {
        // Database connection parameters.
        String url       = "jdbc:mysql://localhost:3306/javaproject";
        String user      = "root";
        String password  = "";

        // Open a JDBC connection to the MySQL database.
        conn = DriverManager.getConnection(url, user, password);

    }
    catch( Exception e )
    {
      // Print the error to help diagnose connection issues at runtime.
      System.out.println("Error Occured " + e.toString());
    }
    // Return the opened connection (or null if connection failed).
    return conn;
  }
}
