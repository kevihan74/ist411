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

public class EchoServerMaster extends ClientServer {

    private int clientPortNumber;
    private ServerSocket port;
    private Socket socket;
    private SocketServer ThreadedServer[] = new SocketServer[5];
    private int numberServer = 11000;
    private String port123;
    private int counter = 0;

    /**
     * EchoServer() constructor creates a server object given
     *  it port number and a number representing the number of
     *  clients it can backlog.
     * @param portNum -- an int giving the port number
     * @param nBacklog -- the number of clients that can backlog
     */    
    public EchoServerMaster(int portNum, int nBacklog)  {
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
     *   server repeatedly accepts a connection from clients and
     *   directs them to a separate sever on different socket.
     */
    public void run() {
        try {
            System.out.println("Echo server at " 
                               + InetAddress.getLocalHost() + " waiting for connections ");
            while(true) {
                socket = port.accept();
                System.out.println("Accepted a connection from " + socket.getInetAddress());
                numberServer++;
                port123 = Integer.toString(numberServer);
                ThreadedServer[counter] = new SocketServer(numberServer, 5);
                ThreadedServer[counter].start();
                
                writeToSocket(socket, port123);
                counter++;
                System.out.println("EchoServerMaster: on port " + socket.getLocalPort() + " is sendig you to EchoServer on port " + numberServer);
                System.out.println("Closed the connection\n");
                }

        } catch (IOException e) {
             e.printStackTrace();
        }
    } // run()

     /** 
      *  main() creates an EchoServerMaster at port 10001
      */
    public static void main(String args[]) {
        EchoServerMaster master = new EchoServerMaster(10001,5);
        master.start();
    } // main()
} // EchoServerMaster
