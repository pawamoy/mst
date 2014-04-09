public class MST
{
	public static MSTServer localserver;
	public static MSTClient localclient;
	
	public static void main(String[] args)
	{
		int sleep_msec = 1000;
		
		localserver = new MSTServer();
		localclient = new MSTClient();
		
		localserver.start();
		System.out.println("Local server will be ready in "+sleep_msec/1000+"second(s)...");
		
		try
		{
			Thread.sleep(sleep_msec);
		}
		catch (InterruptedException ie)
		{
			Thread.currentThread().interrupt();
		}
		
		System.out.println("Starting local client");
		localclient.start();
	}
}
