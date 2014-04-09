//~ http://docs.oracle.com/javase/7/docs/technotes/guides/idl/jidlExample.html

import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;

public class MSTServer
{
    public static void main (String[] args)
    {
        InitCommandObject(args);
    }
    
    //~ public static void InitCommandObject(String[] args)
    //~ {
        //~ try
        //~ {
            //~ ORB orb = ORB.init(args, null);
            //~ 
            //~ ICommandImpl command = new ICommandImpl();
            //~ String ior = orb.object_to_string(command);
            //~ System.out.println(ior);
            //~ 
            //~ orb.run();
        //~ }
        //~ catch (org.omg.CORBA.SystemException se)
        //~ {
            //~ System.err.println(se);
        //~ }
    //~ }
    
    public static void InitCommandObject(String args[])
    {
		try
		{
			// create and initialize the ORB
			ORB orb = ORB.init(args, null);

			// get reference to rootpoa & activate the POAManager
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();

			// create servant and register it with the ORB
			ICommandImpl command = new ICommandImpl();
			command.setORB(orb); 

			// get object reference from the servant
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(command);
			ICommand href = ICommandHelper.narrow(ref);
			  
			// get the root naming context
			// NameService invokes the name service
			org.omg.CORBA.Object objRef =
			  orb.resolve_initial_references("NameService");
			// Use NamingContextExt which is part of the Interoperable
			// Naming Service (INS) specification.
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			// bind the Object Reference in Naming
			String name = "Command";
			NameComponent path[] = ncRef.to_name( name );
			ncRef.rebind(path, href);

			System.out.println("ICommandServer ready and waiting ...");

			// wait for invocations from clients
			orb.run();
		} 
        
		catch (Exception e)
		{
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}
		  
		System.out.println("ICommandServer Exiting ...");
	}
}
