/*
Write a program in Java that will connect to the IP address: 127.0.0.1 and to the port specified in the standard input, via TCP protocol.
The program will receive 20 bytes of data from the connection and then display it in the standard output.
However, data will not be sent in one segment, so the program should continue working until all data is received.
 */

package Cyberskiller.ProgrammingTCPClientWithReducedThroughputJava;

import java.util.*;
import java.net.*;
import java.io.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int port = scanner.nextInt();
        try {
            Socket socket = new Socket("127.0.0.1", port);
            byte[] arr = new byte[20];
            int len=0;
            while (len<20) {
                byte[] tmpArr = socket.getInputStream().readAllBytes();
                System.arraycopy(tmpArr, 0, arr, len, tmpArr.length);
                len+=tmpArr.length;
            }
            socket.close();
            System.out.println(new String(arr, 0, 20));
        } catch (IOException e){
            System.out.println("Smth wrong");
        }
    }
}
