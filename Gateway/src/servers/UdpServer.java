package servers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;

import task.Submiter;

public class UdpServer implements IOTServers
{
	private static final int PORT = 5555;
	private Submiter submiter;
	private DatagramChannel udpserver;
	
	public UdpServer(Submiter submiter)
	{
		this.submiter = submiter;
	}
	
	@Override
	public void createServer()
	{
	     // The port we'll listen on
	      SocketAddress localport = new InetSocketAddress("localhost", PORT);

		  try 
		  {
		    	// create and bind a DatagramChannel to listen on.
		    	udpserver = DatagramChannel.open();
		    	udpserver.socket().bind(localport);
		    	// Specify non-blocking mode for both channels, since our
		    	// Selector object will be doing the blocking for us.
				udpserver.configureBlocking(false);
		  } 
		  catch (IOException e) {e.printStackTrace();}   
	}

	@Override
	public void runServer() 
	{
		IOTSelector selector = IOTSelector.getInstance(submiter);
		
		try 
		{
			selector.openSelector(null, udpserver);
		} catch (ClosedChannelException e)
		{e.printStackTrace();}
		
		if(IOTSelector.isRunning == false)
		{
			selector.start();
		}
	}

}
