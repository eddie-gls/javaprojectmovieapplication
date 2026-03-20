
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 932562301
 */
public class userDaolmpl implements userDAO {
    public void addUser(user user)
  {
    Connection dbConnection = null;
    Statement statement=null;

    String sql = "insert into employee values(" + user.getId() + ","             + "'" + user.getMail()
                    + "'" + "," + user.getpassword() + ","             + "'" +user.getStatu()+")";

    try
    {
      DataSource dataSource = new DataSource();
      dbConnection = dataSource.createConnection();
      statement = dbConnection.createStatement();
   
      statement.executeUpdate(sql);

      System.out.println("Record is inserted into User table for  User : " + user.getMail());

    }
    catch( SQLException e )
    {

      e.printStackTrace();

    }
    finally
    {

      if( statement != null )
      {
        try
        {
          statement.close();
        }
        catch( SQLException e )
        {
          e.printStackTrace();
        }
      }

      if( dbConnection != null )
      {
        try
        {
          dbConnection.close();
        }
        catch( SQLException e )
        {
          e.printStackTrace();
        }
      }

    }

  }
}
