/*
 * IST 411 Program #4
 * File: ExecuteExample.java
 * Description: Program to populate the columns 
 * in the table SALESHISTORY4 with values
 *
 * @author Bill Cantor
 * Modified by: Kevin Hansen
 * @version 1.0 2/9/19
 */

//package databaseTest;
import java.util.*;
import java.io.*;
import java.sql.*;

/* Class ExecuteEample is loged into a database
* based on credentials stored in the file db.properties
* and writes values into the table SALESHISTORY4
*/
public class DatabaseSetup  
{
    public static void main (String args[]) 
    {
        String className=null;
        String url=null;
        String user = null;
        String password = null;
        
        try
        {
            ResourceBundle resources;
            InputStream in = null;
            ResourceBundle newResources;

            in = ClassLoader.getSystemResourceAsStream("db.properties");

            resources = new PropertyResourceBundle(in);

            in.close();

            className = resources.getString("jdbc.driver");
            url = resources.getString("jdbc.url");
            System.out.println(url);
            user = resources.getString("jdbc.user");
            password = resources.getString("jdbc.password");
        } // end try
        
        catch (Exception exp)
        {
            System.out.println("Couldn't load resources." + exp);
            System.exit(-1);
        } // end catch
        
        try
        {
            Class.forName(className);
        } // end try
        
        catch (Exception e) 
        {
            System.out.println("Failed to load  driver.");
            return;
        } // end catch
        
        try
        {
            Connection con = DriverManager.getConnection(url,user,password);   
              
            Statement stmt = con.createStatement();
            
            String tmpString;
            
            /*
            * original code tied to the String tmpString
            * used to create the inital table
            */
            tmpString = ("CREATE TABLE motherboards" + 
                         "(ProductID int," +
                         "Brand varchar(255), " +
                         "ProdName varchar(255), " +
                         "Price double, " + 
                         "Chip varchar(255), " +
                         "primary key (price))"); 
            
                        
            /*
            tmpString = ("INSERT INTO SALESHISTORY4 (PRODUCTID, PRODNAME, PRICE, TRNSDATE)" +
                         "VALUES (1235, 'computer', 1000, '1/19/2019')");
            */
            stmt.execute(tmpString);
            
            /*
            System.out.println("Created Sales History table");
            String[] brand = {"Asus", "Gigabyte", "MSI", "ASRock", "Asus", "MSI", "Gigabyte", "ASRock"};
            String[] prod = {"ASUS ROG DOMINUS EXTREME", "GIGABYTE Z390 AORUS XTREME WATERFORCE LGA 1151 (300 Series)", "MSI MEG Z390 GODLIKE LGA 1151 (300 Series)", "ASRock Z390 Phantom Gaming 9 LGA 1151 (300 Series)", "ASUS ROG Zenith Extreme Alpha X399", "MSI MEG X399 CREATION sTR4", "GIGABYTE X399 AORUS Gaming 7 sTR4", "ASRock X399 Phantom Gaming 6 sTR4"};
            double[] price = {1,799.99, 899.99, 566.34, 233.99, 649.99, 549.99, 369.99, 249.99};
            String[] chip = {"Intel", "Intel", "Intel", "AMD", "AMD", "AMD", "AMD", "AMD"};
            for (int i=0;i<brand.length;i++) {
                tmpString = ("INSERT INTO  MOTHERBOARDS (PRODUCTID, BRAND, PRODNAME, PRICE, CHIP)" +
                         "VALUES ('" + brand[i] + "', '" + prod[i] + "', " + price[i] + ", '" + chip[i] + "')"); 
                stmt.execute(tmpString);
                        }; */
            
            stmt.close();
        
            con.close();
        } // end try
        
        catch (Exception e) 
        {
            System.out.println(e);
        } // end catch
    } // end main
} // end ExecuteExample

