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