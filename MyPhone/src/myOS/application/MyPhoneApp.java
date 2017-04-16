package myOS.application;

import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

import myOS.MyOS;

abstract public class MyPhoneApp {

	////////////////////////////////////////////////////////////////////////////
	/* CONSTANT Variables */
	
	public static final int APP_STATUS_UNSET = 0;
	public static final int APP_STATUS_EXECUTED = 1;
	public static final int APP_STATUS_EXIT = 2;
	
	////////////////////////////////////////////////////////////////////////////
	/* MEMBER Variables */
	
	private static String appDirectory = "/apps/" + MyPhoneApp.class.getName();
	private MyOS os;
	private Map<String, MessageProcedure> messageProcedures;
	private int status;
	
	////////////////////////////////////////////////////////////////////////////
	/* CONSTRUCTORS */
	
	public MyPhoneApp()
	{
		messageProcedures = new HashMap<String, MessageProcedure>();
		status = APP_STATUS_UNSET;
	}
	
	////////////////////////////////////////////////////////////////////////////
	/* METHODS */
	
	public void SetOS(MyOS os)
	{
		this.os = os;
	}
	
	public void requestDisplay(String webFile)
	{
		os.Display(webFile);
	}
	
	public void executeJavascript(String javascript)
	{
		os.getDisplayManager().executeJavascript(javascript);
	}
	
	public void processMessage(String id, Object[] parameters)
	{
		if(messageProcedures.containsKey(id))
		{
			messageProcedures.get(id).process(id, parameters);
		}
	}
	
	/**
	 * Makes a message procedure in the app. The message can be sent if the
	 * app web calls sendNSCommand() function.
	 * @param id the ID of the message.
	 * @param messageProcedure messageProcedure interface.
	 */
	protected void registerMessageProcedure(String id, MessageProcedure messageProcedure)
	{
		messageProcedures.put(id, messageProcedure);
	}
	
	
	/**
	 * Define the message procedures and etc. before executing.
	 */
	abstract public void initialize(String parameter);
	abstract public void exit();
	
	public void execute(String parameter)
	{
		initialize(parameter);
		status = APP_STATUS_EXECUTED;
	}
	
	public int getStatus()
	{
		return status;
	}
	
	public MyOS getOS()
	{
		return os;
	}
	
	public static ImageIcon getIconImage()
	{
		return new ImageIcon(appDirectory + "/" + "icon.gif");
	}
	
	public String getAppDirectory()
	{
		return appDirectory;
	}
}
