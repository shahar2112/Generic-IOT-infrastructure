package servers;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

import gateway.Httphandler;
import task.Submiter;

public class IOTHttpServer implements IOTServers
{
	HttpServer server;
	Submiter submit;
	
	//constructor
	public IOTHttpServer(Submiter submit) 
	{
		this.submit = submit;
	}
	
	
	@Override
	public void createServer()
	{
		try
		{
			server = HttpServer.create(new InetSocketAddress(8700), 0);
			server.createContext("/test", new Httphandler(submit));
		} 
		catch (IOException e) {e.printStackTrace();}
	}

	@Override
	public void runServer()
	{	
		server.start();	
	}

}
