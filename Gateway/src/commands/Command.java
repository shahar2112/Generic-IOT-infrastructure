package commands;

import com.google.gson.JsonObject;

import response.ResponseHandler;

public interface Command 
{
	public boolean execute(ResponseHandler rh, JsonObject data);
}
