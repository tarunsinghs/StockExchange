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


    public int compareTo( Trader other )
    {
        int result = screenName.compareToIgnoreCase( other.getName() );
        return result;
    }


    public boolean equals( Object other )
    {
        if ( other instanceof Trader )
        {
            throw new ClassCastException();
        }
        else
        {
            if ( this.compareTo( (Trader)other ) == 0 )
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }


    public void openWindow()
    {
        myWindow = new TraderWindow( this );

        while ( mailbox.peek() != null )
        {
            String msg = mailbox.remove();
            myWindow.showMessage( msg );
        }
    }


    public boolean hasMessages()
    {
        return ( mailbox.peek() != null );
    }


    public void receiveMessage( String msg )
    {
        mailbox.add( msg );

        if ( myWindow != null )
        {
            while ( mailbox.peek() != null )
            {
                String mg = mailbox.remove();
                myWindow.showMessage( mg );
            }
        }

    }


    public void getQuote( String symbol )
    {
        brokerage.getQuote( symbol, this ); // this error will go away when
                                            // 'getQuote' is written in the
                                            // Brokerage class

    }


    public void placeOrder( TradeOrder order )
    {
        brokerage.placeOrder( order ); // this error will go away when
                                       // 'placeOrder' is written in the
                                       // Brokerage class
    }


    public void quit()
    {
        brokerage.logout( this ); // this error will go away when 'logout' is
                                  // written in the Brokerage class
        myWindow = null;
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
