import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class Communication extends UnicastRemoteObject implements CommInterface
{
	public Communication() throws RemoteException {}
	
	public boolean Message(String msg) throws RemoteException
	{
		System.out.println(msg);
		return false;
	}
}
