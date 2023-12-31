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
        int serverPort = getPort();
        InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
        DatagramPacket packetToServer = getDatagram(serverAddress, serverPort);
        DatagramSocket socket = new DatagramSocket();
        socket.send(packetToServer);
        DatagramPacket packetFromServer = receiveDatagram(socket, 20);
        String data = getStringData(packetFromServer);
        System.out.println(data);
    }

    private static DatagramPacket receiveDatagram(DatagramSocket socket, int noBytes) throws IOException {
        DatagramPacket packetBuff = new DatagramPacket(new byte[20], 20);
        socket.receive(packetBuff);
        return packetBuff;
    }

    private static String getStringData(DatagramPacket packetFromServer) {
        byte[] data = packetFromServer.getData();
        return new String(data, 0, data.length );
    }

    private static DatagramPacket getDatagram(InetAddress address, int port){
        byte[] buffer = new byte[1];
        return new DatagramPacket(buffer, buffer.length, address, port);
    }

    private static int getPort() {
        Scanner scanner = new Scanner(System.in);
        return  scanner.nextInt();
    }
}

/*
Things to remember:
InetAddress InetAddress.getByName(String s) - turns IP string into InetAddress object
InetAddress InetAddress.getLocalHost() - returns host IP as InetAddress object
new DatagramPacket(byte[] buffer, int length, InetAddress IPAddress, int port)
new DatagramPacket(byte[] buffer, int length) - creates buffer for socket.receive
new DatagramSocket() - creates datagram socket
socket.send(Datagram datagram)
socket.receive(Datagram datagram)
datagram.getData()

Remember that everytime there are external resources (INetAddress.getLocalHost, INetAddress.getByName() etc.) you usually have to
deal with IOException
 */