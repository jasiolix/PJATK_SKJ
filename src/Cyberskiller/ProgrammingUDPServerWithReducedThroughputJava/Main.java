package Cyberskiller.ProgrammingUDPServerWithReducedThroughputJava;

/**
 * Write a UDP server in Java. Use the provided template for this purpose. The server should await the data.
 * After the client has sent data, the server will receive many datagrams of data so that the total
 * size of data equals to 20 bytes. The program should display the data in the standard output.
 * Sample output:
 * SUCCESS
 */
import java.util.*;
import java.net.*;
import java.io.*;
import util.SocketFactory;

class Main
{
    public static void main(String[] args) throws IOException
    {
        DatagramSocket socket = SocketFactory.buildSocket();
        DatagramPacket datagramBuff = new DatagramPacket(new byte[20], 20);
        byte[] dataArr = new byte[20];
        int dataSize=0;
        while(dataSize<20){
            socket.receive(datagramBuff);
            System.arraycopy(datagramBuff.getData(), 0, dataArr, dataSize, datagramBuff.getLength());
            dataSize+=datagramBuff.getLength();
        }
        String message = new String(dataArr, 0, 20);
        System.out.println(message);
    }
}
