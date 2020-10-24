package response;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class UdpResponse implements ResponseHandler
{
	SocketAddress clientAddress;
	DatagramChannel udpserver;
	
	public UdpResponse(SocketAddress clientAddress, DatagramChannel udpserver) 
	{
		this.clientAddress = clientAddress;
		this.udpserver = udpserver;
	}

	@Override
	public void ackHandler() 
	{
		try
		{
			String response = "from udp - great - 200";
            byte[] bs = response.getBytes(StandardCharsets.UTF_8);
            ByteBuffer responseBuffer = ByteBuffer.wrap(bs);	
			udpserver.send(responseBuffer, clientAddress);
			System.out.println(response);
			
		} catch (IOException e) {e.printStackTrace();}
	}

	@Override
	public void noAckHandler()
	{
		try
		{
			String response = "from udp - error - 401";
            byte[] bs = response.getBytes(StandardCharsets.UTF_8);
            ByteBuffer responseBuffer = ByteBuffer.wrap(bs);	
			udpserver.send(responseBuffer, clientAddress);
			
		} catch (IOException e) {e.printStackTrace();}
		
	}
}
