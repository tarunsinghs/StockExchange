import java.lang.reflect.*;
import java.util.*;


/**
 * 
 * This is the Trader Class Creates a trader, has various functions that include
 * getting the name, password send and receive messages, etc.
 *
 * @author Andrea Pan
 * @version Mar 20, 2017
 * @author Period: 5
 * @author Assignment: JMCh19_SafeTrade
 *
 */
public class Trader implements Comparable<Trader>
{
    private Brokerage brokerage;

    private String screenName, password;

    private TraderWindow myWindow;

    private Queue<String> mailbox;


    /**
     * Creates an instance of trader
     * 
     * @param brokerage
     *            the brokerage
     * @param name
     *            the name
     * @param pswd
     *            the password
     */
    public Trader( Brokerage brokerage, String name, String pswd )
    {
        this.brokerage = brokerage;
        this.screenName = name;
        this.password = pswd;
        this.mailbox = new PriorityQueue<String>();
    }


    /**
     * 
     * Gets the name of the trader
     * 
     * @return the name
     */
    public String getName()
    {
        return this.screenName;
    }


    /**
     * 
     * Gets the password of the trader
     * 
     * @return the password
     */
    public String getPassword()
    {
        return this.password;
    }


    /**
     * Compare to function
     * 
     * @param other
     *            the other trader
     * @return int the num
     */
    public int compareTo( Trader other )
    {
        int result = screenName.compareToIgnoreCase( other.getName() );
        return result;
    }


    /**
     * Equals function
     * 
     * @param other
     *            the other trader
     * @return boolean true or false
     */
    public boolean equals( Object other )
    {
        return compareTo( (Trader)other ) == 0;
    }


    /**
     * 
     * Function called to open the window
     */
    public void openWindow()
    {
        myWindow = new TraderWindow( this );

        while ( mailbox.peek() != null )
        {
            String msg = mailbox.remove();
            myWindow.showMessage( msg );
        }
    }


    /**
     * Check to see if mailbox has messages
     * 
     * @return boolean true or false
     */
    public boolean hasMessages()
    {
        return ( mailbox.peek() != null );
    }


    /**
     * 
     * Gets the message and displays it to user
     * 
     * @param msg
     *            the msg to be displayed
     */
    public void receiveMessage( java.lang.String msg )
    {
        mailbox.add( msg );
        if ( myWindow != null )
        {
            while ( hasMessages() )
            {
                myWindow.showMessage( mailbox.remove() );
            }
        }
    }


    /**
     * 
     * Get quote of stock
     * 
     * @param symbol
     *            the stock symbol
     */
    public void getQuote( String symbol )
    {
        brokerage.getQuote( symbol, this );

    }


    /**
     * 
     * Place order function
     * 
     * @param order
     *            the order
     */
    public void placeOrder( TradeOrder order )
    {
        if ( order == null )
            return;

        brokerage.placeOrder( order );
    }


    /**
     * 
     * Quits the application
     */
    public void quit()
    {
        brokerage.logout( this );
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
