package Cyberskiller.TCP_CLIENT_PROGRAMMING;

import java.util.*;
import java.net.*;
import java.io.*;

class Sock{
    public static String getFlag(String hostName, Integer port) {
        byte[] data = new byte[20];
        try {
            Socket socket = new Socket(hostName, port);

            socket.getInputStream().read(data, 0, 20);
            socket.close();
        } catch (IOException e){
            System.err.println(e);
        }
        return new String(data, 0, 20);
    }
}

public class ProgrammingTCPClientJava {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Integer port = scanner.nextInt();
        System.out.println(Sock.getFlag("127.0.0.1", port));
    }
}
