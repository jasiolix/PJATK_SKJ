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


public class TCPServer {
    public static void main(String[] args) throws IOException{
        int port = getPort(args);
        ServerSocket serverSocket = createServerSocket(port);
        while(true){
            new Thread( () -> {
                try {
                    Socket socket = getSocket(serverSocket);
                    String message = getMessage(socket);
                    if (message.equals("FINISH")) {
                        socket.close();
                    }
                    else {
                        String response = getResponse(message);
                        sendResponse(response, socket);
                    }
                } catch (IOException ignore){}
            }).start();
        }
    }

    private static void sendResponse(String response, Socket socket) throws IOException {
        socket.getOutputStream().write(response.getBytes());
    }

    private static String getResponse(String message) {
        String header = getHeader(message);
        String[] data = getData(message);
        return switch (header){
            case "Welcome" -> getWelcomeResult();
            case "COUNT" -> getCountResult(data);
            case "DIVISIBLE" -> getDivisibleResult(data);
            default -> "";
        };
    }

    private static String getDivisibleResult(String[] data) {
        int x = Integer.parseInt(data[0]);
        return x%2==0? "NUMBER DIVISIBLE" : "NUMBER NOT DIVISIBLE";
    }

    private static String[] getData(String message) {
        String[] arr = message.split("\\s");
        String[] dataArr = Arrays.copyOfRange(arr, 1, arr.length);
        return dataArr;
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

    private static String getMessage(Socket socket) throws IOException {
        byte[] arr = socket.getInputStream().readAllBytes();
        return new String(arr);
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