import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class Communication extends UnicastRemoteObject implements CommInterface
{
	public Communication() throws RemoteException {}
	
	public boolean SendCommand(String cmd, String args) throws RemoteException
	{
		System.out.println(cmd + ": " + args);
		return false;
	}
}
