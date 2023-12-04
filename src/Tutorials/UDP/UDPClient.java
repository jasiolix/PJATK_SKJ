package Tutorials.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


class UDPClient {

	public static void main(String[] args) throws IOException {
		InetAddress address = InetAddress.getLocalHost(); //wysyłamy do siebie
		int port = 60671; //taki port bo to wartość funkcji server.getLocalPort() w UDPserver.java
	
		int n = 20;
		
		byte[] queryBuff = String.valueOf(n).getBytes();
		DatagramPacket query = new DatagramPacket(queryBuff, queryBuff.length, address, port);
		
		DatagramSocket socket = new DatagramSocket();
		
		socket.send(query);
		
		System.out.println("Wyslalem liczbe "  + n);
		
		byte[] buff = new byte[UDP.MAX_DATAGRAM_SIZE];
		DatagramPacket packet = new DatagramPacket(buff, buff.length);
		System.out.println(packet.getLength());
		socket.receive(packet);
		System.out.println(packet.getLength());
		String str = new String(packet.getData(), 0, packet.getLength()).trim();
		int fn = Integer.parseInt(str);
		
		System.out.println("Dostalem liczbe "  + fn);
		
		socket.close();
	}

}
