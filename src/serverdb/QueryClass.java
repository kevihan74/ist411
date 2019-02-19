
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package databaseTest;
 
public class QueryClass extends SocketServer{
    private String tabStr;
    private ServerSocket port;
    private Socket socket;
    
    public void changeQuery(String str)
    {   
        //////////////////
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
        
        ////////////////
        if (str.toLowerCase().contains("read")) {
            if (str.toLowerCase().contains("all")) {
                str = str.toLowerCase().replace("read ", "").replace("all", "");
	        str = ("SELECT * FROM " + str.toUpperCase());
                System.out.println(str.toUpperCase());
	      }
              else if (str.toLowerCase().contains("motherboard")) {
                 str = str.toLowerCase().replace("read ", "").replace("motherboard ", "");
                 str = ("SELECT * FROM CUSTOMER WHERE PRODUCTID=" + str);
                 System.out.println(str);
	       }
	       else if (str.toLowerCase().contains("processor")) {
                 str = str.toLowerCase().replace("read ", "").replace("processor ", "");
                 str = ("SELECT * FROM EMPLOYEE WHERE CHIPID=" + str);
                 System.out.println(str);
	       }
          }
       
        else if (str.toLowerCase().contains("write")) {
            if (str.toLowerCase().contains("customer")) {
                str = str.toLowerCase().replace("write ", "").replace("customer ", "");
	        str = ("INSERT INTO CUSTOMER (CUSTID, FIRSTNAME, LASTNAME)" +
                       " VALUES (" + str.toUpperCase() + ")");
                System.out.println(str);
                tabStr = "Customer";
	            }
                   else if (str.toLowerCase().contains("employee")) {
	              str = str.toLowerCase().replace("write ", "").replace("employee ", "");
	              str = ("INSERT INTO EMPLOYEE (EMPID, FIRSTNAME, LASTNAME) VALUES (" + str.toUpperCase() + ")");
                      tabStr = "Employee";
	            }
                   System.out.println("Table " + tabStr + " written to");
                   
                }
             else
             System.out.println(str + " is an invalid input\n");
        //////////////////////////////
        try {
                    Connection con = DriverManager.getConnection(url,user,password);   
           
                    Statement stmt = con.createStatement();
            
                    String tmpString;
                    /////
                    tmpString = (str);
                    if (tmpString.contains("INSERT")) {
                    stmt.execute(tmpString);
                    }
                    
                    if (tmpString.contains("SELECT"))
                    {
                    ResultSet rs = stmt.executeQuery(tmpString);
                    ResultSetMetaData rsmd = rs.getMetaData();

                    int numberOfColumns = rsmd.getColumnCount();
                    int rowCount = 1;
                    String xyz = "";
                    while (rs.next())
                    {
                    for (int i = 1; i <= numberOfColumns; i++)
                    {
                        //System.out.print(rs.getString(i).substring(0,1).toUpperCase() + rs.getString(i).substring(1).toLowerCase() + " ");
                        xyz = xyz + rs.getString(i).substring(0,1).toUpperCase() + rs.getString(i).substring(1).toLowerCase() + " ";
                    }
                    
                    rowCount++;
                    }
                    System.out.println(xyz);
                    writeToSocket(socket, xyz);

                    }
                stmt.close();
        
                con.close();
        
                }
                
                catch (Exception e) {
                        e.printStackTrace(System.out);
                        }
        //////////////////////////
    }
}
