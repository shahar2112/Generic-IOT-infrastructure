package response;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

public class HttpResponse implements ResponseHandler
{
	private HttpExchange responseHandler;
	private final long NoBODY = -1;
	private final int OK = 200;
	private final int ERROR = 404;
	
	public HttpResponse(HttpExchange responseHandler)
	{
		this.responseHandler = responseHandler;
	}
	
	@Override
	public void ackHandler() 
	{
		try
		{
			responseHandler.sendResponseHeaders(OK, NoBODY);
			responseHandler.close();
		} catch (IOException e) {e.printStackTrace();}
	}

	@Override
	public void noAckHandler() 
	{
		try 
		{
			responseHandler.sendResponseHeaders(ERROR, NoBODY);
			responseHandler.close();
		} catch (IOException e) {e.printStackTrace();}
	}

}
