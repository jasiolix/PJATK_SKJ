package Tutorials.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


class UDPServer {
	private DatagramSocket server;
	
	public UDPServer() throws SocketException  {
		initializeServer();
	}
	
	private void initializeServer() throws SocketException {
		server = new DatagramSocket();
		System.out.println("Server listens on: " + server.getLocalPort());
	}
	
	private void service() throws IOException {
		byte[] buff = new byte[UDP.MAX_DATAGRAM_SIZE];
		final DatagramPacket datagram = new DatagramPacket(buff, buff.length);
		
		server.receive(datagram);
		
		new Thread(() ->  {
				int n = Integer.parseInt(new String(datagram.getData(), 0, datagram.getLength()));
				System.out.println("I've got " + n);
				int nFact = Factorial.compute(n);
				byte[] respBuff = String.valueOf(nFact).getBytes();
				int clientPort = datagram.getPort();
				InetAddress clientAddress = datagram.getAddress();
				DatagramPacket resp = new DatagramPacket(respBuff, respBuff.length, clientAddress, clientPort);
				try {
					server.send(resp);
					System.out.println("I've sent " + nFact);
				} catch (IOException e) {
					// do nothing
				}
			}).start();
	}
	
	public void listen() {
		while(true) {
			try {
				service();
			} catch (IOException e) {
				// do nothing
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			new UDPServer().listen();
		} catch (SocketException e) {
			System.out.println("Could not set up the server");
		}
	}

}

/*
REMEMBER:
socket.getLocalPort()
datagram.getHost()
datagram.getAddress()

Every function has its own stack, so you can call function which creates variables with the same name
and it wont be a problem (final Datagram datagram in code makes sense)
 */
