import org.omg.CORBA.*;

public class MSTClient
{
    public static void main (String[] args)
    {
        try
        {
            ORB orb = orb.init(args, null);
            
            ICommandImpl command = new ICommandImpl();
            String ior = orb.object_to_string(command);
            System.out.println(ior);
            
            orb.run();
        }
        catch (org.omg.CORBA.SystemException se)
        {
            
        }
    }
}
