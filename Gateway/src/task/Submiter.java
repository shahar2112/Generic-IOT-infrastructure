package task;

import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public class Submiter implements Consumer<Task>
{
	ExecutorService tp;
	
	public Submiter(ExecutorService tpool)
	{
		this.tp = tpool;
	}
	
	@Override
	public void accept(Task task) 
	{
		tp.execute(task);
	}

}
