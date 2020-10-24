package gateway;

import java.io.InputStreamReader;
import java.io.Serializable;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class HttpMsgObj implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public String command;
	public JsonObject data;
	
	public static HttpMsgObj parse(InputStreamReader json)
	{
		Gson gson = new Gson();
		
		return gson.fromJson(json, HttpMsgObj.class);
	}
}
