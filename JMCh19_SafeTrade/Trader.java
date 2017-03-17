import java.lang.reflect.*;
import java.util.*;


/**
 * Represents a stock trader.
 */
public class Trader implements Comparable<Trader>
{
    private Brokerage brokerage;

    private String screenName, password;

    private TraderWindow myWindow;

    private Queue<String> mailbox;


    // TODO complete class
    public Trader( Brokerage brokerage, String name, String pswd )
    {
        this.brokerage = brokerage;
        screenName = name;
        password = pswd;
    }
    
    public String getName()
    {
        return screenName;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public int compareTo(Trader other)
    {
        
    }


    //
    // The following are for test purposes only
    //
    protected Queue<String> mailbox()
    {
        return mailbox;
    }


    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this Trader.
     */
    public String toString()
    {
        String str = this.getClass().getName() + "[";
        String separator = "";

        Field[] fields = this.getClass().getDeclaredFields();

        for ( Field field : fields )
        {
            try
            {
                if ( field.getType().getName().equals( "Brokerage" ) )
                    str += separator + field.getType().getName() + " "
                        + field.getName();
                else
                    str += separator + field.getType().getName() + " "
                        + field.getName() + ":" + field.get( this );
            }
            catch ( IllegalAccessException ex )
            {
                System.out.println( ex );
            }

            separator = ", ";
        }

        return str + "]";
    }
}
