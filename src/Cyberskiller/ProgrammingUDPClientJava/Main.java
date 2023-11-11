/*
Write a program in Java that will send any data to the IP address: 127.0.0.1 and to the port specified in the standard input, via UDP protocol. Then the program will receive one datagram of data from the server (data size 20 bytes) and display it in the standard output.
 */

package Cyberskiller.ProgrammingUDPClientJava;

import java.util.*;
import java.net.*;
import java.io.*;

class Main
{
    public static void main(String[] args) throws IOException {
        DatagramPacket packetToServer = getDatagram("127.0.0.1", getPort());
        DatagramSocket socket = getSocket();
        sendDatagram(socket, packetToServer);
        DatagramPacket packetFromServer = receiveDatagram(socket, 20);
        String data = getStringData(packetFromServer);
        System.out.println(data);
    }

    private static DatagramPacket receiveDatagram(DatagramSocket socket, int noBytes) throws IOException {
        DatagramPacket packetBuff = getDatagram(noBytes);
        socket.receive(packetBuff);
        return packetBuff;
    }

    private static DatagramPacket getDatagram(int i) {
        return new DatagramPacket(new byte[i], i);
    }

    private static void sendDatagram(DatagramSocket socket, DatagramPacket packetToServer) throws IOException {
        socket.send(packetToServer);
    }

    private static String getStringData(DatagramPacket packetFromServer) {
        byte[] data = packetFromServer.getData();
        return new String(data, 0, data.length );
    }


    private static DatagramSocket getSocket() throws SocketException {
        return new DatagramSocket();
    }

    private static DatagramPacket getDatagram(String s, int port) throws UnknownHostException {
        byte[] buffer = new byte[1];
        return new DatagramPacket(buffer, buffer.length, InetAddress.getByName(s), port);
    }

    private static int getPort() {
        Scanner scanner = new Scanner(System.in);
        return  scanner.nextInt();
    }
}