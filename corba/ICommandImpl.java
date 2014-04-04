import org.omg.CORBA.*;

public class ICommandImpl extends _ICommandImplBase
{
    public boolean SendCommand(String cmd, String args)
    {
        System.out.println(cmd + ": " + args);
        
        return false;
    }
}
