import java.util.*;
import java.lang.reflect.*;
import java.text.DecimalFormat;


/**
 * Represents a stock in the SafeTrade project
 */
public class Stock
{
    public static DecimalFormat money = new DecimalFormat( "0.00" );

    private String stockSymbol;

    private String companyName;

    private double loPrice, hiPrice, lastPrice;

    private int volume;

    private PriorityQueue<TradeOrder> buyOrders, sellOrders;


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


    public String getQuote()
    {
        String message = this.companyName + " (" + this.stockSymbol + ")" + "\n"
            + "Price: " + this.lastPrice + "  hi: " + this.hiPrice + "  lo: "
            + this.loPrice + "  vol: " + this.volume + "\n";
        String ask = "Ask: none";
        String bid = "Bid: none";
        if ( sellOrders.peek() != null )
        {
            ask = "Ask: " + sellOrders.peek().getPrice() + " size: "
                + sellOrders.peek().getShares();
        }
        if ( buyOrders.peek() != null )
        {
            bid = " Bid: " + buyOrders.peek().getPrice() + " size: "
                + buyOrders.peek().getShares();
        }
        return message + ask + " " + bid;

    }


    public void placeOrder( TradeOrder order )
    {
        String message = "";
        if ( order.isBuy() )
        {
            this.buyOrders.add( order );
            message = "New Order: Buy " + order.getSymbol() + " ("
                + this.companyName + ")" + "\n" + order.getShares()
                + " shares at ";
            if ( !order.isMarket() )
            {
                message += money.format( order.getPrice() );
            }
            else
            {
                message += "market";
            }
        }
        else
        {
            this.sellOrders.add( order );
            message = "New Order: Sell " + order.getSymbol() + " ("
                + this.companyName + ")" + "\n" + order.getShares()
                + " shares at ";
            if ( !order.isMarket() )
            {
                message += money.format( order.getPrice() );
            }
            else
            {
                message += "market";
            }
        }
        order.getTrader().receiveMessage( message );
        executeOrders();

    }


    public void executeOrders()
    {
        if ( sellOrders.isEmpty() || buyOrders.isEmpty() )
        {
            return;
        }

        TradeOrder buy = buyOrders.peek();
        TradeOrder sell = sellOrders.peek();

        Trader buyer = buy.getTrader();
        Trader seller = sell.getTrader();

        double actualPrice = 0;

        if ( buy.isLimit() && sell.isMarket() )
        {
            actualPrice = buy.getPrice();
        }

        else if ( buy.isMarket() && sell.isLimit() )
        {
            actualPrice = sell.getPrice();
        }

        else if ( buy.isMarket() && sell.isMarket() )
        {
            actualPrice = lastPrice;
        }

        if ( buy.isLimit() && sell.isLimit()
            && buy.getPrice() >= sell.getPrice() )
        {
            actualPrice = sell.getPrice();
        }

        // sell price > buy price
        else
        {
            return; // does nothing
        }

        int shares = Math.min( buy.getShares(), sell.getShares() );

        buy.subtractShares( shares );
        sell.subtractShares( shares );

        if ( buy.getShares() == 0 )
        {
            buyOrders.remove( buy );
        }

        if ( sell.getShares() == 0 )
        {
            sellOrders.remove( sell );
        }

        if ( actualPrice < loPrice )
        {
            loPrice = actualPrice;
        }

        if ( actualPrice > hiPrice )
        {
            hiPrice = actualPrice;
        }

        volume += shares;
        lastPrice = actualPrice;

        buyer.receiveMessage( "You bought: " + shares + " " + stockSymbol
            + " at " + money.format( actualPrice ) + " amt "
            + money.format( shares * actualPrice ) );
        seller.receiveMessage( "You sold: " + shares + " " + stockSymbol
            + " at " + money.format( actualPrice ) + " amt "
            + money.format( shares * actualPrice ) );

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
