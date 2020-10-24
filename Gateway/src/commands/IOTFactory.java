package commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class IOTFactory
{
	private Map<String, Supplier<? extends Command>> map;
	private static int CAPACITY = 10;
	private static IOTFactory instanceFactory = null;
	
	private IOTFactory()
	{
		this.map = new HashMap<>(CAPACITY);
	}
	
	public static IOTFactory getInstance()
	{
		if(instanceFactory == null)
		{	
			synchronized (IOTFactory.class) 
			{				
				if(instanceFactory == null)
				{				
					instanceFactory = new IOTFactory();
				}
			}
		}
		
		return instanceFactory;
	}
	
	public void add(String key, Supplier<? extends Command> creator)
	{
		this.map.put(key, creator);
	}
	
	/* return null if this key does not exist
	 * data is the parameter of the ctor.	*/
	public Command createCommand(String key)
	{
		if(!map.containsKey(key))
		{
			return null;
		}
		Supplier<? extends Command> creator = map.get(key);
		return creator.get();
	}
}

