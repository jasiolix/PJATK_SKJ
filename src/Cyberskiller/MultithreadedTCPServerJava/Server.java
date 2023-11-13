/*
Write a multithreaded TCP server. The program should be able to serve many clients.

The program should receive 20 bytes of data and then display it in the standard output.

The server’s port will be provided as the first argument of the program.
 */

package Cyberskiller.MultithreadedTCPServerJava;

import java.net.*;
import java.io.*;


public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
        while (true) {
            Socket connectionSocket = serverSocket.accept();
            new SocketThread(connectionSocket).start();
        }
    }
}

class SocketThread extends Thread{
    private Socket socket;
    SocketThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        byte[] arr = new byte[20];
        try {
            socket.getInputStream().read(arr, 0, 20);
            socket.close();
            System.out.println(new String(arr, 0, 20));
        } catch (IOException ignored) {}
    }
}

/*
remember:
args[0] is the first argument of the program (String)
while(true) loop with serverSocket.accept() here is necessary, because we want the server to run infinitely and wait
for a connection request from a client.
 */


