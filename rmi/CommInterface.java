import java.rmi.Remote; 
import java.rmi.RemoteException; 

public interface CommInterface extends Remote
{
    public boolean Message(String msg, int id) throws RemoteException;
	public boolean Broadcast(int id, int num, String msg) throws RemoteException;
}
