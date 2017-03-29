import java.util.*;
import java.lang.reflect.*;
import java.text.DecimalFormat;


/**
 * 
 * Stock class in StockExchange project Creates a stock object, with various
 * functions and methods
 *
 * @author Avinash Jain
 * @version Mar 20, 2017
 * @author Period: 5
 * @author Assignment: JMCh19_SafeTrade
 *
 */
public class Stock
{
    public static DecimalFormat money = new DecimalFormat( "0.00" );

    private String stockSymbol;

    private String companyName;

    private double loPrice, hiPrice, lastPrice;

    private int volume;

    private PriorityQueue<TradeOrder> buyOrders, sellOrders;


    /**
     * Creates an instance of a stock
     * 
     * @param symbol
     *            the stock symbol
     * @param name
     *            the stock name
     * @param price
     *            the stock price
     * 
     */
    public Stock( String symbol, String name, Double price )
    {
        this.stockSymbol = symbol;
        this.companyName = name;
        this.lastPrice = price;
        this.loPrice = price;
        this.hiPrice = price;
        this.volume = 0;
        this.sellOrders = new PriorityQueue<TradeOrder>( 10,
            new PriceComparator() );
        this.buyOrders = new PriorityQueue<TradeOrder>( 10,
            new PriceComparator( false ) );
    }


    /**
     * 
     * Gets the quote of the stock
     * 
     * @return the quote
     */
    public String getQuote()
    {
        String quote = companyName + " (" + stockSymbol + ")" + "\n" + "Price: "
            + lastPrice + " hi: " + hiPrice + " lo: " + loPrice + " vol: "
            + volume + "\n";
        String ask = "Ask: none";
        String bid = "Bid: none";
        if ( sellOrders.peek() != null )
        {
            ask = "Ask: " + sellOrders.peek().getPrice() + " size: "
                + sellOrders.peek().getShares();
        }
        if ( buyOrders.peek() != null )
        {
            bid = "Bid: " + buyOrders.peek().getPrice() + " size: "
                + buyOrders.peek().getShares();
        }
        return quote + ask + " " + bid;

    }


    /**
     * 
     * Places the order of the stock
     * 
     * @param order
     *            takes in the order
     */
    public void placeOrder( TradeOrder order )
    {
        String str = "New order: ";

        if ( order == null ) 
        {
            return;
        }

        if ( order.isBuy() )
        {
            buyOrders.add( order ); 
            str += "Buy ";
        }
        else
        {
            sellOrders.add( order ); 
            str += "Sell ";
        }

        str += order.getSymbol() + " (" + this.companyName + ")" + "\n" + order.getShares() + " shares at ";

        if ( !order.isMarket() )
        {
            str += money.format( order.getPrice() ); 
        }
        else
        {
            str += "market";
        }

        order.getTrader().receiveMessage( str ); 
        executeOrders(); 
    }


    /**
     * 
     * Executes the order of the class
     */
    protected void executeOrders()
    {
        if ( buyOrders.isEmpty() || sellOrders.isEmpty() )
        {
            return;
        }

        /* 1. check the top sell order and the top buy order */
        TradeOrder buy = buyOrders.peek();
        TradeOrder sell = sellOrders.peek();
        Trader buyer = buy.getTrader();
        Trader seller = sell.getTrader();

        /* determine price first */
        double price = 0;

        if ( buy.isLimit() && sell.isLimit() && buy.getPrice() >= sell.getPrice() )
        {
            price = sell.getPrice();
        }
        else if ( buy.isLimit() && sell.isMarket() )
        {
            price = buy.getPrice(); // use buyer's price
        }
        else if ( buy.isMarket() && sell.isLimit() )
        {
            price = sell.getPrice(); // seller's price
        }
        else if ( buy.isMarket() && sell.isMarket() )
        { // if both market, use last price.
            price = lastPrice;
        }
        else // sell price > buy price
        {
            return; // does nothing
        }

        /*
         * 2. Figures out how many shares can be traded, which is the smallest
         * of the numbers of shares in the two orders.
         */
        int nShares = Math.min( buy.getShares(), sell.getShares() );

        /* 3. Subtracts the traded number of shares from each order */
        buy.subtractShares( nShares );
        sell.subtractShares( nShares );

        if ( buy.getShares() == 0 ) // no more buying order,
        {
            buyOrders.remove( buy );
        }

        if ( sell.getShares() == 0 )
        {
            sellOrders.remove( sell );
        }

        /* 4. Updates the day's low price, high price, and volume. */
        if ( price < loPrice ) // traded at lower price, update loPrice.
        {
            loPrice = price;
        }

        if ( price > hiPrice )
        {
            hiPrice = price; // update high price.
        }

        volume += nShares;
        lastPrice = price;

        /*
         * 5. Sends a message to each of the two traders involved in the
         * transaction.
         */
        buyer.receiveMessage( "You bought: " + nShares + " " + stockSymbol + " at " + money.format( price ) + " amt "
            + money.format( nShares * price ) );
        seller.receiveMessage( "You sold: " + nShares + " " + stockSymbol + " at " + money.format( price ) + " amt "
            + money.format( nShares * price ) );

        /*
         * 6. Repeats steps 1-5 for as long as possible, that is as long as
         * there is any movement in BOTH buy / sell order queues.
         */
        if ( !buyOrders.isEmpty() && !sellOrders.isEmpty() )
        {
            executeOrders();
        }
    }


    protected String getStockSymbol()
    {
        return stockSymbol;
    }


    protected String getCompanyName()
    {
        return companyName;
    }


    protected double getLoPrice()
    {
        return loPrice;
    }


    protected double getHiPrice()
    {
        return hiPrice;
    }


    protected double getLastPrice()
    {
        return lastPrice;
    }


    protected int getVolume()
    {
        return volume;
    }


    protected PriorityQueue<TradeOrder> getBuyOrders()
    {
        return buyOrders;
    }


    protected PriorityQueue<TradeOrder> getSellOrders()
    {
        return sellOrders;
    }


    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this Stock.
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
