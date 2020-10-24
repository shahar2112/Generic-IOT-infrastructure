package gateway;


import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import commands.CR;
import commands.Command;
import commands.EUR;
import commands.IOT;
import commands.IOTFactory;
import commands.PR;
import servers.IOTServers;

public class Gateway 
{
	private ArrayList<IOTServers> servers;
	private ExecutorService tpool;
	
	public Gateway(ArrayList<IOTServers> servers, ExecutorService tpool)
	{
		this.servers = servers;
		this.tpool = tpool;
		initCommandsFactory();
	}
	
	private void initCommandsFactory()
	{
		//create the commands and add them to factory 
		IOTFactory.getInstance().add("CR", () -> {return new CR();});
		IOTFactory.getInstance().add("PR", () -> {return new PR();});
		IOTFactory.getInstance().add("EUR", () -> {return new EUR();});
		IOTFactory.getInstance().add("IOT", () -> {return new IOT();});
	}
	
	public void runServers()
	{
		for(IOTServers s : servers)
		{
			s.createServer();
			s.runServer();
		}
	}

}
