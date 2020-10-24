package servers;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import com.google.gson.Gson;

import gateway.TcpUdpMsgObj;
import response.TcpResponse;
import response.UdpResponse;
import task.Submiter;
import task.Task;

public class IOTSelector extends Thread
{
	 // The Selector object is what allows us to block while waiting
    // for activity on either of the two channels.
	private Selector selector;
    private static final int BUFFER_SIZE = 1024;
    private Submiter submiter;
    public static boolean isRunning = false;
    
    ServerSocketChannel tcpserver;
    DatagramChannel udpserver;
    
    private static IOTSelector instance = null;
    
    private IOTSelector(Submiter submiter)
    {
    	try 
    	{
			selector = Selector.open();
		} catch (IOException e) {e.printStackTrace();}

        this.submiter = submiter;
    }
    
    public static IOTSelector getInstance(Submiter submiter)
    {
    	if (null == instance)
		{
			synchronized (IOTSelector.class) 
			{
				if (null == instance)
				{
					instance = new IOTSelector(submiter);
				}
			}
		}
		return instance;
    }
    
    
    public void openSelector(ServerSocketChannel tcpserver, DatagramChannel udpserver) throws ClosedChannelException
    {
    	// Register the channels with the selector, and specify what
    	// conditions we'd like the Selector to wake up for.
    	registerChannels(tcpserver, udpserver);
    	
    	this.tcpserver = tcpserver;
    	this.udpserver = udpserver;
    }
    
    @Override
    public void run()
    {
    	// Now loop forever, processing client connections
    	if(isRunning == false)
    	{
    		isRunning = true;
    		for (;;) 
    		{
    			try
    			{
    				// Handle per-connection -Wait for a client to connect
    				selector.select();

    				// Get the SelectionKey objects for the channels that have
    				// activity on them. These are the keys returned by the
    				// register() methods above. They are returned in a
    				// java.util.Set.
    				Set<SelectionKey> keys = selector.selectedKeys();
    				Iterator<SelectionKey> i = keys.iterator();
    				
    				// Iterate through the Set of keys.
    				while ( i.hasNext()) 
    				{
    					// Get a key from the set, and remove it from the set
    					SelectionKey key = i.next();
    					i.remove();
    					
    					// Get the channel associated with the key
    					Channel c = (Channel) key.channel();
    					
    					// Now test the key and the channel to find out
    					// whether something appened on the TCP or UDP channel
    					if (key.isAcceptable() && c == tcpserver) 
    					{
    						processAcceptEvent(tcpserver);
    					} 
    					else if (key.isReadable() && c == udpserver)
    					{
    						processReadEventUdp(udpserver);
    					}
    					else if(key.isReadable())
    					{
    						processReadEventTcp(key);
    					}	
    				}
    			}catch (java.io.IOException e) {e.printStackTrace();};
    		}
    	}
    }
    
    /***************************Utility methods**********************************/
    
    private TcpUdpMsgObj bufferToMsgObj(ByteBuffer myBuffer)
    {
		try 
		{
		    // convert ByteBuffer to String then return msg object
			String converted = new String(myBuffer.array());
			
			String trimmed = converted.trim();
			
			Gson gson = new Gson();
			TcpUdpMsgObj msgObj  = gson.fromJson(trimmed, TcpUdpMsgObj.class);
			
			return msgObj;
		}
		catch (Exception e)
		{e.printStackTrace();}
		return null;
    }
    
    private void registerChannels(ServerSocketChannel tcpserver, DatagramChannel udpserver)
    {
    	try 
    	{
	     	if(tcpserver != null)
	    	{    		
					tcpserver.register(selector, SelectionKey.OP_ACCEPT);
	    	}
	    	
	    	if(udpserver != null)
	    	{    		
	    		udpserver.register(selector, SelectionKey.OP_READ);
	    	}
    	} 
    	catch (ClosedChannelException e) {e.printStackTrace();}
    }
    
    private void processAcceptEvent(ServerSocketChannel tcpserver)
    {
    	System.out.println("Connection Accepted...");

		try 
		{
			// A client has attempted to connect via TCP.
			// Accept the connection now.
			SocketChannel client = tcpserver.accept();
			client.configureBlocking(false);
			// Register interest in reading this channel
			client.register(selector, SelectionKey.OP_READ);
		} 
		catch (IOException e) {e.printStackTrace();}
    }
    
    private void processReadEventUdp(DatagramChannel udpserver)
    {
		// A UDP datagram is waiting. Receive it now,
		// noting the address it was sent from.
		SocketAddress clientAddress;
		try
		{
			ByteBuffer receiveBuffer = ByteBuffer.allocate(BUFFER_SIZE);
			clientAddress = udpserver.receive(receiveBuffer);
			//change the msg received in buffer to a msg Object
			TcpUdpMsgObj msgObj = bufferToMsgObj(receiveBuffer);
			
			UdpResponse udpresponse = new UdpResponse(clientAddress, udpserver);
			Task task = new Task(msgObj.command, msgObj.data, udpresponse);
			
			submiter.accept(task);
		} 
		catch (IOException e) {e.printStackTrace();}
    }
    
    
    private void processReadEventTcp(SelectionKey key)
    {
    	// the channel method returns a selectableChannel associated with the key 
		SocketChannel myClient = (SocketChannel) key.channel();
		// Set up 1k buffer to read data into
		ByteBuffer myBuffer = ByteBuffer.allocate(BUFFER_SIZE);
		
		// reads from channel to myBuffer
		try 
		{
			if(myClient == null)
			{
				return;
			}
			
			myClient.read(myBuffer);
		}
		catch (IOException e) {e.printStackTrace();}
		
		//change the msg received in buffer to a msg Object
		TcpUdpMsgObj msgObj = bufferToMsgObj(myBuffer);
		if(msgObj == null)
		{
			return;
		}
		
        TcpResponse tcpresponse = new TcpResponse(myClient);

		Task task = new Task(msgObj.command, msgObj.data, tcpresponse);

		submiter.accept(task);
		
    }
}
