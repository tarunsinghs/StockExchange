import java.lang.reflect.*;


/**
 * Represents a buy or sell order for trading a given number of shares of a
 * specified stock.
 * 
 * @author Andrea Pan
 * @version Mar 20, 2017
 * @author Period: 5
 * @author Assignment: JMCh19_SafeTrade
 * 
 */
public class TradeOrder
{
    private Trader trader;

    private String symbol;

    private boolean buyOrder;

    private boolean marketOrder;

    private int numShares;

    private double price;

/** 
 * Create trade order instance
 * @param trader trader 
 * @param symbol symbol
 * @param buyOrder buyORder
 * @param marketOrder marketOrder
 * @param numShares numShares
 * @param price price
 */
    public TradeOrder(
        Trader trader,
        String symbol,
        boolean buyOrder,
        boolean marketOrder,
        int numShares,
        double price )
    {
        this.trader = trader;
        this.symbol = symbol;
        this.buyOrder = buyOrder;
        this.marketOrder = marketOrder;
        this.numShares = numShares;
        this.price = price;

    }

/**
 * 
 * Gets the trader
 * @return the trader
 */
    public Trader getTrader()
    {
        return trader;
    }

/**
 * 
 * Gets the symbol
 * @return returns the symbol
 */
    public String getSymbol()
    {
        return symbol;
    }

/**
 * 
 * checks to see if buy works
 * @return buy boolean
 */
    public boolean isBuy()
    {
        return buyOrder == true;
    }

/**
 * 
 * Checks to see if order is sell
 * @return true or false
 */
    public boolean isSell()
    {
        return buyOrder == false;
    }

/**
 * CHecks to see if order is market
 * @return true or false
 */
    public boolean isMarket()
    {
        return marketOrder == true;
    }

/**
 * Checks to see if order is limit
 * @return true or false
 */
    public boolean isLimit()
    {
        return marketOrder == false;
    }

/**
 * 
 * Gets the shares of the stock
 * @return int value
 */
    public int getShares()
    {
        return numShares;
    }

/**
 * 
 * Gets the price of the stock
 * @return double stock
 */
    public double getPrice()
    {
        return price;
    }

/**
 * 
 * Subtracts shares
 * @param shares shares
 */
    public void subtractShares( int shares )
    {
        if ( shares > numShares )
        {
            throw new IllegalArgumentException();
        }
        else
        {
            numShares = numShares - shares;

        }

    }


    //
    // The following are for test purposes only
    //
    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this TradeOrder.
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
