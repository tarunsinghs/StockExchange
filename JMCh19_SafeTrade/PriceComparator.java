import java.lang.reflect.Field;


/**
 * 
 * Price Comparator Class A price comparator class that creates the comparator
 * we use in our priority queues
 *
 * @author Avinash Jain
 * @version Mar 28, 2017
 * @author Period: 5
 * @author Assignment: JMCh19_SafeTrade
 */
public class PriceComparator implements java.util.Comparator<TradeOrder>
{

    public boolean ascending;


    /**
     * Creates the PriceComparator
     */
    public PriceComparator()
    {
        ascending = true;
    }


    /**
     * Creates a price comparator with a parameter
     * 
     * @param asc
     *            takes in ascending parameter
     */
    public PriceComparator( boolean asc )
    {
        ascending = asc;
    }


    /**
     * Creates the compare method between two trade orders
     * 
     * @order1 First order
     * @order2 Second order
     * 
     * @return int what to return
     */
    public int compare( TradeOrder order1, TradeOrder order2 )
    {
        if ( order1.isMarket() && order2.isMarket() )
            return 0;
        else if ( order1.isMarket() && order2.isLimit() ) 
            return -1;
        else if ( order1.isLimit() && order2.isMarket() ) 
            return 1;
        else 
        { 
            if ( ascending == true )
                return (int)( ( order1.getPrice() - order2.getPrice() ) * 100 );
            else
                return (int)( ( order2.getPrice() - order1.getPrice() ) * 100 );
        }
    }

/**
 * To string method
 * @return the string version
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
