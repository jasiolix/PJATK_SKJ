package Cyberskiller.ProgrammingUDPClientWithReducedThroughputJava;

import java.util.*;
import java.net.*;
import java.io.*;

/**
Write a program in Java that will send any data to the IP address: 127.0.0.1 and to the port specified in the standard
input, via UDP protocol. Then the program will receive many datagrams of data so that the total size of data equals
to 20 bytes. The program should display the data in the standard output.

Sample output:

SUCCESS
 **/
class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        int serverPort = scanner.nextInt();
        InetAddress serverAddress;
        try {
            serverAddress = InetAddress.getByName("127.0.0.1");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        DatagramPacket packetToServer = new DatagramPacket(new byte[0], 0, serverAddress, serverPort);
        byte[] dataFromServer = new byte[20];
        int dataSize = 0;
        DatagramPacket packetFromServer = new DatagramPacket(new byte[20], 20);
        try (DatagramSocket hostSocket = new DatagramSocket()) {
            hostSocket.send(packetToServer);
            while (dataSize!=20){
                hostSocket.receive(packetFromServer);
                System.arraycopy(packetFromServer.getData(), 0, dataFromServer, dataSize, packetFromServer.getLength());
                dataSize+=packetFromServer.getLength();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String message = new String(dataFromServer, 0, 20);
        System.out.println(message);
    }
}

//Zapamietaj:
//po inwokacji datagramSocket.receive datagramPacket.getLength() zwraca nie size of the buffer,
// tylko length of received byte array, (ale buffer.length jest wciąż taki jak był).
