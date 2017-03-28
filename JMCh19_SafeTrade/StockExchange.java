import java.lang.reflect.*;
import java.util.*;


/**
 * This is the stock exchange class Creates an instance of the stock exchange
 * class for our application
 *
 * @author Avinash Jain
 * @version Mar 28, 2017
 * @author Period: 5
 * @author Assignment: JMCh19_SafeTrade
 */
public class StockExchange
{
    private Map<String, Stock> listedStocks;


    /**
     * Creates an instance of the stock exchange
     */
    public StockExchange()
    {
        listedStocks = new HashMap<String, Stock>();

    }


    /**
     * 
     * Returns all the stocks
     * 
     * @param symbol
     *            the symbol
     * @param name
     *            the name
     * @param price
     *            the price
     */
    public void listStock( String symbol, String name, double price )
    {
        listedStocks.put( symbol, new Stock( symbol, name, price ) );
    }


    /**
     * 
     * Gets the quote form a symbol
     * 
     * @param symbol
     *            this is the symbol
     * @return a quote
     */
    public String getQuote( String symbol )
    {
        return listedStocks.get( symbol ).getQuote();
    }


    /**
     * 
     * Places an order in the exchange
     * 
     * @param order
     *            this is the order
     */
    public void placeOrder( TradeOrder order )
    {
        if ( order == null )
            return;
        Stock s = listedStocks.get( order.getSymbol() );
        if ( s != null )
        {
            s.placeOrder( order );
            return;
        }
        String str = order.getSymbol() + " not found";
        order.getTrader().receiveMessage( str );
    }


    //
    // The following are for test purposes only
    //
    protected Map<String, Stock> getListedStocks()
    {
        return listedStocks;
    }


    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this StockExchange.
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
