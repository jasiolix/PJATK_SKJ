//Write a program in Java that will connect to the IP address: 127.0.0.1 and to the port specified in the standard input,
//via TCP protocol. The program will receive 20 bytes of data from the connection and then display it in the standard output.

package Cyberskiller.ProgrammingTCPClientJava;

//import java.util.*;
//import java.net.*;
//import java.io.*;
//
//class Sock{
//    public static String getFlag(String hostName, Integer port) {
//        byte[] data = new byte[20];
//        try {
//            Socket socket = new Socket(hostName, port);
//
//            socket.getInputStream().read(data, 0, 20);
//            socket.close();
//        } catch (IOException e){
//            System.err.println(e);
//        }
//        return new String(data, 0, 20);
//    }
//}
//
//public class Cyberskiller.ProgrammingTCPClientJava {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        Integer port = scanner.nextInt();
//        System.out.println(Sock.getFlag("127.0.0.1", port));
//    }
//}

import java.util.*;
import java.net.*;
import java.io.*;

class Sock{
    public static String getFlag(String hostname, int port) {
        byte[] arr = new byte[20];
        try {
            Socket socket = new Socket(hostname, port);
            socket.getInputStream().read(arr, 0, 20);
            socket.close();
        } catch (IOException e) {
            System.err.println(e);
        }
        return new String(arr, 0, 20);
    }
}
class Main{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int port = scanner.nextInt();
        System.out.println(Sock.getFlag("name", port));
    }
}









































