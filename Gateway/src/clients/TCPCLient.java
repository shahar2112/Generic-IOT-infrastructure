
package clients;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;


public class TCPCLient 
{	
	public static void send(JsonObject jsonObject, int port, InetAddress host) // port = 9999; host = InetAddress.getLocalHost();
	{
		try(Socket socket = new Socket(host, port);
			BufferedOutputStream bufOutputStream = new BufferedOutputStream(socket.getOutputStream()); 
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bufOutputStream);)
		{
			Gson gson = new Gson();
			JsonWriter jsonWriter = gson.newJsonWriter(outputStreamWriter);
			gson.toJson(jsonObject, jsonWriter);
			
			outputStreamWriter.flush();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		final int PORT = 9999;
		InetAddress HOST = null;
		try 
		{
			HOST = InetAddress.getLocalHost();
		} catch (UnknownHostException e) 
		{
			e.printStackTrace();
		}
		
		JsonObject messageJson = new JsonObject();
		messageJson.addProperty("command", "PR");
		
		JsonObject dataJson = new JsonObject();
		dataJson.addProperty("data1", "BLA BLA1");
		dataJson.addProperty("data2", "BLA BLA2");
		
		messageJson.add("data", dataJson);
		
		TCPCLient.send(messageJson, PORT, HOST);
	}
}
