import java.rmi.* ; 
import java.net.MalformedURLException ; 

public class MSTClient
{
    public static void main (String[] args)
    {
        try
        {
			ICommand command = (ICommand)Naming.lookup("rmi:"+args[0]+"/command");
			
			command.SendCommand("MSG1", "FUMIER");
			command.SendCommand("MSG2", "TOM LA BRICOLE");
		}
		catch (MalformedURLException mue)
		{
			System.out.println(mue);
		}
		catch (NotBoundException nbe)
		{
			System.out.println(nbe);
		}
		catch (RemoteException re)
		{
			System.out.println(re);
		}
    }
}
