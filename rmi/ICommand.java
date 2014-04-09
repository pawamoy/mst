import java.rmi.Remote; 
import java.rmi.RemoteException; 

public interface ICommand extends Remote
{
    public boolean SendCommand(String cmd, String args) throws RemoteException;
}
