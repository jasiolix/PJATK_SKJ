package Cyberskiller.TCPServerJavaVersionA;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

class TCPClient {
    public static void main(String[] args) throws IOException {
        while (true) {
            Socket socket = createSocket();
            String message = getMessage();
            send(message, socket);
            String response = receive(socket);
            System.out.println(response);
        }
    }

    public static String receive(Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return in.readLine();
        // return new String(socket.getInputStream().readAllBytes()); <- THIS DOESNT WORK, bc readAllBytes doesn't detect end of stream, its a BLOCKING method!
    }

    public static void send(String message, Socket socket) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeBytes(message+"\n");
        // socket.getOutputStream().write((message+"\n").getBytes()); <- THIS WORKS, but generally DataOutputStream is more useful!
    }

    private static String getMessage() throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    private static Socket createSocket() throws IOException {
        return new Socket(InetAddress.getLocalHost(), 6789);
    }
}
