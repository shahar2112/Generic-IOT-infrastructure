package gateway;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import servers.IOTHttpServer;
import servers.IOTServers;
import servers.TcpServer;
import servers.UdpServer;
import task.Submiter;

public class RunGateway 
{
	private static final int MAX_T = 10;
	
	public static void main(String[] args) 
	{
		//create thread pool
		ExecutorService tpool = Executors.newFixedThreadPool(MAX_T);
		Submiter submiter = new Submiter(tpool);
		ArrayList<IOTServers> servers = new ArrayList<>();
		
		//add servers to the servers list
		servers.add(new IOTHttpServer(submiter));
		servers.add(new TcpServer(submiter));
		servers.add(new UdpServer(submiter));
		
		//create new gateway and add the servers list and thread pool
		Gateway gateway = new Gateway(servers, tpool);
		//create and run all the servers
		gateway.runServers();
	}

}
