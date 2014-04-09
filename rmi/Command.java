import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class Command extends UnicastRemoteObject implements ICommand
{
	public Command() throws RemoteException {}
	
	public boolean SendCommand(String cmd, String args) throws RemoteException
	{
		System.out.println(cmd + ": " + args);
		return false;
	}
}
