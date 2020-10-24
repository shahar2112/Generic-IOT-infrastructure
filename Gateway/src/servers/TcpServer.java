package servers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ServerSocketChannel;

import task.Submiter;

public class TcpServer implements IOTServers
{
	private static final int PORT = 9999;
	private ServerSocketChannel tcpserver;
	private Submiter submiter;
	
	public TcpServer(Submiter submiter)
	{
		this.submiter = submiter;
	}
	
	@Override
	public void createServer() 
	{
		try
		{
		      // The port we'll listen on
		      SocketAddress localport = new InetSocketAddress(PORT);

		      // Create and bind a tcp channel to listen for connections on.
		      tcpserver = ServerSocketChannel.open();
		      tcpserver.socket().bind(localport);

		      // Specify non-blocking mode for both channels, since our
		      // Selector object will be doing the blocking for us.
		      tcpserver.configureBlocking(false);
		}
		catch (IOException e) {e.printStackTrace();}
	}
	

	@Override
	public void runServer() 
	{
		IOTSelector selector = IOTSelector.getInstance(submiter);
		
		try 
		{
			selector.openSelector(tcpserver, null);
		} catch (ClosedChannelException e)
		{e.printStackTrace();}
		
		if(IOTSelector.isRunning == false)
		{
			selector.start();
		}
	}

}
