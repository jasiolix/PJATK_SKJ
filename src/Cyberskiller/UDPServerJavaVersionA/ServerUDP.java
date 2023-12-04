package Cyberskiller.UDPServerJavaVersionA;

import java.util.*;
import java.net.*;
import java.io.*;

/**
 * Write UDP server. Client sending data to server will send them always in the same format. 
 * First byte of the datagram is its sequence number. Next byte is the size of data and next is the data of given size.
 * <p>
 * Server task is to receive appropriate data from client. Server will start receiving appropriate 
 * data after he receives message in which data can be built into string HELLO. Next server should respond with
 * the message in the same format (sequence number can be different) also with data that can be built into string HELLO. 
 * Next server should receive appropriate data from client until it receives all datagrams with sequence numbers: 5, 7 and 11.
 * Then server should join received messages data in ascending order and send it back to client in one message
 * using client's message format. Message should be sent to the port that is used by client and IP address 127.0.0.1.
 * <p>
 * Your program will receive server port as args[0].
 * <p>
 * Clue: Client will send message with data creating string HELLO until he will receive appropriate answer from the server.
 * <p>
 * Standard output will show messages received by the client. If client receives invalid datagram he will print information
 * about decoded number, message length, message and datagram length.
 * <p>
 * Example output:
 * <p>
 * HELLO
 * Lorem ipsum dolor sit ame
 */
public class ServerUDP {
    private static boolean flag5=false;
    private static boolean flag7=false;
    private static boolean flag11=false;
    private static boolean flagHELLO=false;
    
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(Integer.parseInt(args[0]));
        byte[] queryBuffer = new byte[1024];
        DatagramPacket queryPacket = new DatagramPacket(queryBuffer, queryBuffer.length);
        byte[] queryData;
        byte queryId = 0;
        byte queryLength = 0;
        String queryMessage;
        while (!flagHELLO){
            socket.receive(queryPacket);
            queryId = queryPacket.getData()[0];
            queryLength = queryPacket.getData()[1];
            queryData = new byte[queryLength];
            System.arraycopy(queryPacket.getData(), 2, queryData, 0, queryLength);
            queryMessage = new String(queryData,0 , queryLength);
            if(queryMessage.equals("HELLO")) {
                flagHELLO = true;
            }
        }
        String responseMessage = "HELLO";
        byte[] responseData = responseMessage.getBytes();
        byte responseLength = (byte)responseData.length;
        byte responseId = queryId;
        byte[] responseBuffer = new byte[responseLength+2];
        responseBuffer[0] = responseId;
        responseBuffer[1] = responseLength;
        System.arraycopy(responseData, 0, responseBuffer, 2, responseLength);
        DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, queryPacket.getAddress(), queryPacket.getPort());
        socket.send(responsePacket);
        Map<Byte, byte[]> map = new TreeMap<>();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        responseLength = 0;
        responseMessage = null;
        responseData = null;
        responseId = 1;
        responseBuffer = null;
        responsePacket = null;
        while(!(flag5 && flag7 && flag11)){
            socket.receive(queryPacket);
            queryId = queryPacket.getData()[0];
            queryLength = queryPacket.getData()[1];
            queryData = new byte[queryLength];
            System.arraycopy(queryPacket.getData(), 2, queryData, 0, queryLength);
            map.put(queryId, queryData);
            if(queryId==5)
                flag5=true;
            if(queryId==7)
                flag7=true;
            if(queryId==11)
                flag11=true;
        }
        for(byte[] arr : map.values()){
            baos.write(arr);
        }
        responseMessage = baos.toByteArray().toString();
        responseData = responseMessage.getBytes();
        responseLength = (byte)responseData.length;
        responseBuffer = new byte[responseLength+2];
        responseBuffer[0] = responseId;
        responseBuffer[1] = responseLength;
        System.arraycopy(responseData, 0, responseBuffer, 2, responseLength);
        responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, queryPacket.getAddress(), queryPacket.getPort());
        socket.send(responsePacket);
    }
}

