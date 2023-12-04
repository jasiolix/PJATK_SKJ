package Cyberskiller.UDPServerWithMessageFragmentation;

import java.util.*;
import java.net.*;
import java.io.*;

/**
 * Write a UDP server. This server will receive no more than 1024 bytes of data in the standard input.
 * After having received any dataram the server should resend previously read data.
 * <p>
 * Data should be prepared for sending in the following way: It should be divided into packets.
 * Each packet should be up to 12 bytes in size. The first byte is the index number of the packet (starting from 0).
 * The second byte is equivalent to the size of the packet (in most cases it will be 10, only the last packet can be smaller than 10) .
 * Remaining bytes should include the bytes from the specific packet. The packet numeration should allow for
 * data reconstruction in a correct order.
 * <p>
 * Data prepared in such a way should be sent 10 times and the sending
 * process should be cyclical (the server should not send the first packet of data
 * unless the last packet from the previous cycle has been sent).
 * <p>
 * The server’s port will be provided as the first argument of the program.
 */

public class Server {

    public static void main(String[] args) {
        byte[] dataArr;
        try {
            dataArr = new DataInputStream(System.in).readAllBytes();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        List<DatagramPacket> packetList = new ArrayList<>(103); // 1024/10=103
        DatagramPacket dummy = new DatagramPacket(new byte[0], 0);
        try(DatagramSocket socket = new DatagramSocket(Integer.parseInt(args[0]))) {
            socket.receive(dummy);
            for(int i=0;i<dataArr.length/10;i++){
                packetList.add(createDatagram((byte)i, (byte)10, dataArr, dummy.getAddress(), dummy.getPort()));
            }
            if(dataArr.length%10!=0) {
                packetList.add(createDatagram((byte) (dataArr.length / 10), (byte) (dataArr.length % 10),
                        dataArr, dummy.getAddress(), dummy.getPort()));
            }
            for(int i=0; i<10; i++){
                for(DatagramPacket packet : packetList){
                    socket.send(packet);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static DatagramPacket createDatagram(byte index, byte length, byte[] arr, InetAddress address, int port) {
        byte[] byteArr = new byte[length+2];
        byteArr[0] = index;
        byteArr[1] = length;
        int offset = index*10;
        System.arraycopy(arr, offset, byteArr, 2, length);
        return new DatagramPacket(byteArr, byteArr.length, address, port);
    }

}