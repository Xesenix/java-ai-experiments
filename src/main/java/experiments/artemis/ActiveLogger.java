
package experiments.artemis;

import org.slf4j.Logger;


public class ActiveLogger
{

	private boolean active = false;


	private Logger logger;


	public ActiveLogger(Logger logger)
	{
		this.logger = logger;
	}


	public void setActive(boolean active)
	{
		this.active = active;
	}


	public void debug(String format, Object... argArray)
	{
		if (active)
		{
			logger.debug(format, argArray);
		}
	}


	public void info(String format, Object... argArray)
	{
		if (active)
		{
			logger.info(format, argArray);
		}
	}
}