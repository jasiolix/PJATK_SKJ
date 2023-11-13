/*
Write a UDP server in Java. Use the provided template for this purpose. The server should await the data.

After the client has sent the data, the server should receive one datagram of data (20 bytes of data) and display it in the standard output.
 */

package Cyberskiller.ProgrammingUDPServerJava;

import java.util.*;
import java.net.*;
import java.io.*;
import util.SocketFactory;

class Main
{
    public static void main(String[] args) throws IOException
    {
        DatagramSocket socket = SocketFactory.buildSocket();
        DatagramPacket packet = new DatagramPacket(new byte[20], 20);
        socket.receive(packet);
        System.out.println(new String(packet.getData(), 0, 20));
    }
}