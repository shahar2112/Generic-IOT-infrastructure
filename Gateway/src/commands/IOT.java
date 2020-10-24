package commands;

import com.google.gson.JsonObject;

import response.ResponseHandler;

public class IOT implements Command
{
	@Override
	public boolean execute(ResponseHandler rh, JsonObject data) 
	{
		System.out.println("hello i am the command IOT " + data);
		rh.ackHandler();
		return true;
	}	
}
