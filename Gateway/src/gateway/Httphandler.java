package gateway;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import response.HttpResponse;
import task.Submiter;
import task.Task;

public class Httphandler implements HttpHandler {
	private Submiter submiter;

	public Httphandler(Submiter submiter) {
		this.submiter = submiter;
	}

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		// receive the request
		InputStream input = arg0.getRequestBody();

		InputStreamReader req = new InputStreamReader(input);

		// parse the request
		HttpMsgObj msgObj = HttpMsgObj.parse(req);

		// create a new response handler with httpExchange
		HttpResponse httpResponse = new HttpResponse(arg0);

		Task task = new Task(msgObj.command, msgObj.data, httpResponse);

		submiter.accept(task);
	}

}
