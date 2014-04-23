package mstgui;

import java.rmi.Remote; 
import java.rmi.RemoteException; 

public interface CommInterface extends Remote
{
    public boolean Message(String msg) throws RemoteException;
	public boolean Broadcast(int id, int num, String msg) throws RemoteException;
}
