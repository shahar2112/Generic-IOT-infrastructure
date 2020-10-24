package clients;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import gateway.TcpUdpMsgObj;

public class UDPClient
{
	public static void send(JsonObject jsonObject, int port, InetAddress host) // port = 9999; host = InetAddress.getLocalHost();
	{
		try
		{
			// Creating and binding the socket, using datagram for UDP.
			DatagramSocket socket = new DatagramSocket();
			
			// Creating the datagram destination (that is the server socket address).
			InetSocketAddress datagramDestination = new InetSocketAddress ("localhost", port);
		
			// Creating the datagram buffer.
			byte[] datagramBuffer = jsonObject.toString().getBytes("UTF8");
			int datagramBufferOffset = 0;
			int datagramBufferSize = datagramBuffer.length;

			//the datagram with details we send and where to send
			DatagramPacket datagram = new DatagramPacket(datagramBuffer, datagramBufferOffset, datagramBufferSize, datagramDestination);
			socket.send(datagram);	
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		final int PORT = 5555;
		InetAddress HOST = null;
		try 
		{
			HOST = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) 
		{
			e.printStackTrace();
		}
		
		JsonObject messageJson = new JsonObject();
		messageJson.addProperty("command", "CR");
		
		JsonObject dataJson = new JsonObject();
		dataJson.addProperty("data1", "TESTING UDP");
		dataJson.addProperty("data2", "HELLO");
		
		messageJson.add("data", dataJson);
		
		UDPClient.send(messageJson, PORT, HOST);
	}
}
