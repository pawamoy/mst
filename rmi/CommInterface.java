import java.rmi.Remote; 
import java.rmi.RemoteException; 

public interface CommInterface extends Remote
{
    public boolean Message(String msg) throws RemoteException;
}
