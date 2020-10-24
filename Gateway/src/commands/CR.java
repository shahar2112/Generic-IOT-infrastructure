package commands;

import com.google.gson.JsonObject;

import response.ResponseHandler;

public class CR implements Command
{
	@Override
	public boolean execute(ResponseHandler rh, JsonObject data) 
	{
		System.out.println("hello i am the command cr the data is " + data);
		rh.ackHandler();
		return true;
	}	
}
