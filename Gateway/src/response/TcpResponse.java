package response;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class TcpResponse implements ResponseHandler
{
	private SocketChannel myClient;
	private ByteBuffer myBuffer;
	
	public TcpResponse(SocketChannel myClient)
	{
		this.myClient = myClient;
	}
	
	@Override
	public void ackHandler()
	{
		try
		{
			String response = "great - 200";
            byte[] bs = response.getBytes(StandardCharsets.UTF_8);
            ByteBuffer buffer = ByteBuffer.wrap(bs);
			myClient.write(buffer);
			System.out.println(response);
			myClient.close();
		}
		catch (IOException e) {e.printStackTrace();} // send response
	}

	@Override
	public void noAckHandler()
	{
		try
		{
			String response = "error - 401";
            byte[] bs = response.getBytes(StandardCharsets.UTF_8);
            ByteBuffer buffer = ByteBuffer.wrap(bs);
			myClient.write(buffer);
			myClient.close();
		}
		catch (IOException e) {e.printStackTrace();} // send response
	}
}
