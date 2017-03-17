/**
 * A price comparator for trade orders.
 */
public class PriceComparator implements java.util.Comparator<TradeOrder>
{

   
    /**
     * Constructs a price comparator that compares two orders in ascending order. Sets the private boolean ascending flag to true.
     * 
     */
    public PriceComparator()
    {
        
    }
    /**
     * Constructs a price comparator that compares two orders in ascending or descending order. The order of comparison depends on the value of a given parameter. Sets the private boolean ascending flag to asc.
     * @param asc - if true, make an ascending comparator; otherwise make a descending comparator.
     */
    public PriceComparator(boolean asc)
    {
        
    }
    /**
     * Compares two trade orders.
     * @param order1 the first order
     * @param order2 the second order
     * @return errors if order1 and order 2 are not of type limit. If both are of type limit, return the difference between order1 and order 2.
     */
    int compare (TradeOrder order1, TradeOrder order2)
    {
        if (order1.isMarket()&&order2.isMarket())
        {
            return 0;
        }
        else if (order1.isMarket()&&order2.isLimit())
        {
            return -1;
        }
        else if (order1.isLimit()&&order2.isMarket())
        {
            return 1;
        }
        else
        {
            double priceDiff order1.getPrice()-order2.getPrice();
            priceDiff = (double) Math.round(priceDiff*100)/100;
            return math.abs(priceDiff);
        }
    }
    

}
