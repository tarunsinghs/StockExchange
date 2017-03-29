import java.lang.reflect.*;
import java.util.*;


/**
 * 
 * This is the brokerage class This has all the methods of the brokerage class
 * that allows us to use it in the stock exchange application
 *
 * @author Avinash Jain
 * @version Mar 28, 2017
 * @author Period: 5
 * @author Assignment: JMCh19_SafeTrade
 *
 */
public class Brokerage implements Login
{
    private Map<String, Trader> traders;

    private Set<Trader> loggedTraders;

    private StockExchange exchange;


    /**
     * Creates Brokerage
     * 
     * @param exchange
     *            the exchange variable
     */
    public Brokerage( StockExchange exchange )
    {
        this.exchange = exchange;
        traders = new TreeMap<String, Trader>();
        loggedTraders = new TreeSet<Trader>();
    }


    /**
     * Adds a user to loggedTraders
     * 
     * @param name
     *            the user's name
     * @param password
     *            the user's password
     * @return int what to return
     * 
     */
    public int addUser( String name, String password )
    {
        if ( name.length() > 10 || name.length() < 4 )
        {
            return -1;
        }
        if ( password.length() > 10 || password.length() < 2 )
        {
            return -2;
        }
        if ( this.traders.containsKey( name ) )
        {
            return -3;
        }
        else
        {
            traders.put( name, new Trader( this, name, password ) );
            return 0;
        }
    }


    /**
     * Logs in the user
     * 
     * @param name
     *            the user's name
     * @param password
     *            the user's password
     * @return int what to return
     */
    public int login( String name, String password )
    {

        if ( traders.get( name ) == null )
        {
            return -1;
        }
        Trader t = traders.get( name );
        if ( t.getPassword().equals( password ) )
        {
            if ( loggedTraders.contains( t ) )
            {
                return -3;
            }
            else
            {
                loggedTraders.add( t );
                t.openWindow();
                String msg = "Welcome to SafeTrade!";
                t.receiveMessage( msg );
                return 0;
            }
        }
        else
        {
            return -2;
        }
    }


    /**
     * 
     * Logs out the trader
     * 
     * @param trader
     *            which trader to logout
     */
    public void logout( Trader trader )
    {
        loggedTraders.remove( trader );
    }


    /**
     * 
     * Gets the quote from a given symbol and trader
     * 
     * @param symbol
     *            the symbol
     * @param trader
     *            the trader
     */
    public void getQuote( String symbol, Trader trader )
    {
        trader.receiveMessage( exchange.getQuote( symbol ) );
    }


    /**
     * 
     * Places an order
     * 
     * @param order
     *            the order
     */
    public void placeOrder( TradeOrder order )
    {
        if ( order == null )
            return;
        exchange.placeOrder( order );
    }


    protected Map<String, Trader> getTraders()
    {
        return traders;
    }


    protected Set<Trader> getLoggedTraders()
    {
        return loggedTraders;
    }


    protected StockExchange getExchange()
    {
        return exchange;
    }


    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this Brokerage.
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
