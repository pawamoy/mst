import org.omg.CORBA.*;

public class MSTServer
{
    public static void main (String[] args)
    {
        InitCommandObject(args);
    }
    
    public static void InitCommandObject(String[] args)
    {
        try
        {
            ORB orb = ORB.init(args, null);
            
            ICommandImpl command = new ICommandImpl();
            String ior = orb.object_to_string(command);
            System.out.println(ior);
            
            orb.run();
        }
        catch (org.omg.CORBA.SystemException se)
        {
            System.err.println(se);
        }
    }
}
