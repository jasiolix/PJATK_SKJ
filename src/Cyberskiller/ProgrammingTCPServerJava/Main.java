/*
Write a TCP server in Java. Use the provided template for this purpose. The server should await the connection.

After having connected, the server will receive 20 bytes of data from the client and display in the standard output.
 */

package Cyberskiller.ProgrammingTCPServerJava;

import java.net.*;
import java.io.*;
import util.SocketFactory;

class Main
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket socket = SocketFactory.buildSocket();

        Socket connectionSocket = socket.accept();
        byte[] arr = new byte[20];
        connectionSocket.getInputStream().read(arr, 0, 20);
        connectionSocket.close();
        System.out.println(new String(arr, 0, 20));
    }
}

/*
remember:
ServerSocket socket = new ServerSocket(int port);
Socket s = socket.accept();
socket.getInputStream();
try(){} - try with resources closes the resaurces every time, so you dont have to close them

 */