/*
 * IST 411 Program #3
 * File: EchoServer.java
 * Description: This class defines the server object of
 *  a simple client/server application. The application
 *  sets up a socket connection between the client and 
 *  server and simply echos strings input by the user.
 *  The server object reads a string sent by a client
 *  and sends the same string back over the socket.
 *
 * @author Java, Java, Java
 * Modified by: Kevin Hansen
 * @version 1.0 1/30/19
 */
import java.net.*;
import java.io.*;

public class SocketServer extends ClientServer {

    private ServerSocket port;
    private Socket socket;
    /**
     * EchoServer() constructor creates a server object given
     *  it port number and a number representing the number of
     *  clients it can backlog.
     * @param portNum -- an int giving the port number
     * @param nBacklog -- the number of clients that can backlog
     */    
    public SocketServer(int portNum, int nBacklog)  {
        try {
            port = new ServerSocket (portNum, nBacklog);
        } catch (IOException e) {
            e.printStackTrace();
        }     
    } 
 
    /**
     *  run() defines the server thread's main behavior which is
     *   simply to provide service whenever it is requested by a client. 
     *   Since an I/O exception may result, it is handled here. The
     *   server repeatedly accepts a connection from a client and
     *   provides it whatever service is defined in provideService().
     */
    
    public void run() {
        try {
            System.out.println("Echo server at echoserver "
                               + InetAddress.getLocalHost() + " waiting for connections ");
            while(true) {
                socket = port.accept();
                System.out.println("Accepted a connection from " + socket.getInetAddress());
                provideService(socket);
                socket.close();
                System.out.println("Closed the connection\n");
            }
        } catch (IOException e) {
             e.printStackTrace();
        }
    } // run() 

    /**
     *  provideService() defines this server's service, which consists
     *   of simply echoing whatever string it receives from the client.
     *  The server's protocol calls for it to begin by saying hello
     *  and end by saying goodbye. Isn't it polite?
     */

    protected void provideService (Socket socket) {
        String str="";
        QueryClass qc = new QueryClass();
        try {
            writeToSocket(socket, "Hello. You are connected to " + socket.getLocalPort() + "\n"); //change to display port number 11001
            do {     
                str = readFromSocket(socket);
                if (str.toLowerCase().equals("goodbye"))
                    writeToSocket(socket, "Goodbye\n");
                else
                    qc.changeQuery(str);
                    //writeToSocket( socket, "You said '" + str + "'\n");
            }  while (!str.toLowerCase().equals("goodbye"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // provideServer() 

} // EchoServer
