/*
 * IST 411 Program #3
 * File: EchoClient.java
 * Description: This class defines the client object of
 *  a simple client/server application. The application
 *  sets up a socket connection between the client and
 *  server and simply echos strings input by the user. 
 *  The client object serves as the user interface. It
 *  accepts user input and sends it to the server.
 *
 * @author Java, Java, Java
 * Modified by: Kevin Hansen
 * @version 1.0 1/30/19
 */

import java.net.*;
import java.io.*;

public class EchoClient extends ClientServer {

     protected static Socket socket;
     private static int newPort = 11000;

     /**
      *  EchoClient() constructor creates a client object
      *   given the URL and port number of a server that this
      *   client will connect to
      *  @param url -- a String giving the server's URL
      *  @param port -- an int giving the server's port number
      */
     public EchoClient(String url, int port) {
         try {
             socket = new Socket(url, port);
             newPort = Integer.parseInt(readFromSocket(socket));
             socket = new Socket(url, newPort);
             System.out.println("CLIENT: connected to " + url + ":" + port);
             System.out.println("CLIENT: port number " + socket.getLocalPort());
             newPort = newPort + 1;
             
         } catch (Exception e) {
             e.printStackTrace();
             System.exit(1);
         }
     } // EchoClient()

     /**
      *  run() defines the client thread's main behavior which is
      *   simply to request service from its server. Since an I/O
      *   exception may result, it is handled here.
      */
     public void run() {
         try {
             requestService(socket);  
             socket.close();
             System.out.println("CLIENT: connection closed");
             
         } catch (IOException e) { 
             System.out.println(e.getMessage());
             e.printStackTrace(); 
         }
         
     } // run()

    /**
     *  requestService() implements the details of the service
     *   request. In this case it accepts a line of input from
     *   the user and passes it along to the server. The protocol
     *   with the server requires that the server say "Hello" first.
     *  @param socket -- the Socket connection to the server
     */
    protected void requestService(Socket socket) throws IOException {         
        String servStr = readFromSocket(socket);          // Check for "Hello"
        System.out.println("SERVER: " + servStr);         // Report the server's response
        if (servStr.substring(0,5).equals("Hello")) {
            System.out.println("CLIENT: type a line or 'goodbye' to quit"); // Prompt the user
            String userStr = "";
            do {
                userStr = readFromKeyboard();                   // Get input from user
                writeToSocket(socket, userStr + "\n");          // Send it to server
                servStr = readFromSocket(socket);               // Read the server's response
                System.out.println("SERVER: " + servStr);       // Report the server's response
            } while (!userStr.toLowerCase().equals("goodbye")); // Until user says 'goodbye'
        }
        
    } // requestService()

    /**
     *  readFromKeyboard() reads a line of input from the keyboard
     */
    protected String readFromKeyboard( ) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("INPUT: ");
        String line = input.readLine();
        return line;
    } // readFromKeyboard()

    /**
     *  main() creates a client object given the URL and port number
     *   of the echo server
     */
    
    public static void main (String args[]) {
        EchoClient client = new EchoClient("localhost", 10001);
        client.start();
    } // main() 
} // EchoClient
