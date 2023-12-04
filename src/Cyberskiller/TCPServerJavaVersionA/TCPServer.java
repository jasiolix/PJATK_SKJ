package Cyberskiller.TCPServerJavaVersionA;

/*
Write a TCP server in Java. Server port will be given in first argument of the ran program args[0]. Server should wait for the connection. After establishing connection server will receive message from a client with command and data. After receiving message server should send appropriate message back.

First of possible client messages is Welcome. Server should respond to a client with message containing Welcome client!.

Second of possible client messages is COUNT X Y, where X and Y are integers. Server should respond: RESULT Z, where Z is a sum of X and Y.

Another message from client could be DIVISIBLE X, where X is an integer. Server should respond: NUMBER DIVISIBLE, if X is divisible by 2. In another case server should send: NUMBER NOT DIVISIBLE.

Last of possible messages is FINISH, server should immediately close the connection.

When running the code you will see client's output on standard output.

Example of client messages:

Welcome
COUNT 2 5
DIVISIBLE 58
DIVISIBLE 57
FINISH
Attention: Messages may be sent in any order. Size of the letters matters!

 */
import java.io.*;
import java.net.*;
import java.util.Arrays;


class TCPServer {

    public static void main(String[] args) throws IOException{
        //int port = getPort(args);
        ServerSocket serverSocket = createServerSocket(6789);
        while(true){
            Socket socket = getSocket(serverSocket);
            new Thread( () -> {
                try {
                    String message = TCPClient.receive(socket);
                    if (message.equals("FINISH")) {
                        socket.close();
                    } else {
                        String response = getResponse(message);
                        TCPClient.send(response, socket);
                        socket.close();
                    }
                } catch (IOException ignore){}
            }).start();
        }
    }

    private static String getResponse(String message) {
        String header = getHeader(message);
        String[] data = getData(message);
        return switch (header){
            case "Welcome" -> getWelcomeResult();
            case "COUNT" -> getCountResult(data);
            case "DIVISIBLE" -> getDivisibleResult(data);
            default -> "Wrong message!";
        };
    }

    private static String getDivisibleResult(String[] data) {
        int x = Integer.parseInt(data[0]);
        return x%2==0? "NUMBER DIVISIBLE" : "NUMBER NOT DIVISIBLE";
    }

    private static String[] getData(String message) {
        String[] arr = message.split("\\s");
        return Arrays.copyOfRange(arr, 1, arr.length);
    }

    private static String getCountResult(String[] data) {
        int x = Integer.parseInt(data[0]);
        int y = Integer.parseInt(data[1]);
        int z = x+y;
        return "RESULT "+z;
    }

    private static String getWelcomeResult() {
        return "Welcome client!";
    }

    private static String getHeader(String message) {
        return message.split("\\s")[0];
    }

    private static ServerSocket createServerSocket(int port) throws IOException {
        return new ServerSocket(port);
    }
    private static Socket getSocket(ServerSocket serverSocket) throws IOException {
        return serverSocket.accept();
    }

    private static int getPort(String[] args) {
        return Integer.parseInt(args[0]);
    }
}

/*
I got 0 points from cyberskillers bc:
1. I didnt practice enough, next time I need to write code faster.
2. I assumed there wont be writing to socket, bc on cyberskiller tasks we only read from sockets, so I had
   to remind myself which took a lot of time
3. I didn't understand how to use output and input streams, in particular that socket.getInputStream().readAllBytes()
   won't work bc readAllBytes() method has to detect end of stream, BUT END OF STREAM IN CASE OF SOCKETS ONLY HAPPENS
   IF SOMEONE CLOSES THE CONNECTION!
 */