package task;

import response.ResponseHandler;

import com.google.gson.JsonObject;

import commands.Command;
import commands.IOTFactory;

public class Task implements Runnable
{
	private String command;
	private JsonObject data;
	private ResponseHandler responseHandler;
	
	public Task(String command, JsonObject data, ResponseHandler rh)
	{
		this.command = command;
		this.data = data;
		this.responseHandler = rh;
	}

	@Override
	public void run() 
	{
		System.out.println("command = " + command);
		System.out.println("data = " + data);
		
		Command mycommand = IOTFactory.getInstance().createCommand(command);
		
		System.out.println("mycommand is - " + mycommand + " data is " + data);
		
		//execute the created command with the received data
		mycommand.execute(responseHandler, data);
	}
}
