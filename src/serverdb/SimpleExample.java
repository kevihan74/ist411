package databaseTest;
import java.sql.*;
import java.io.*;
import java.util.*;

public class SimpleExample
{
    public static void main (String args[])
    {

        try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        }
        catch (Exception e)
        {
            System.out.println(" driver failed to load.");
            System.exit(-1);
        }

        try
        {
            Connection con =
                    DriverManager.getConnection("jdbc:derby://localhost:1527/inventory", "app", "app");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM CUSTOMER");

            ResultSetMetaData rsmd = rs.getMetaData();

            int numberOfColumns = rsmd.getColumnCount();
            int rowCount = 1;

            while (rs.next())
            {
                for (int i = 1; i <= numberOfColumns; i++)
                {
                    System.out.print(rs.getString(i) + " ");
                }
                System.out.println("");
                rowCount++;
            }

            stmt.close();

            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}


