package gateway;

import java.io.Reader;
import java.io.Serializable;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class TcpUdpMsgObj implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public String command;
	public JsonObject data;
	
	public static TcpUdpMsgObj parse(Reader json)
	{
		Gson gson = new Gson();
		
		return gson.fromJson(json, TcpUdpMsgObj.class);
	}
}
