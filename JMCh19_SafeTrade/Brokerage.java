import java.lang.reflect.*;
import java.util.*;

/**
 * Represents a brokerage.
 */
public class Brokerage implements Login
{
    private Map<String, Trader> traders;
    private Set<Trader> loggedTraders;
    private StockExchange exchange;

    public Brokerage(StockExchange exchange)
    {
        this.exchange = exchange; 
    }
    
    public int addUser(String name, String password) {
        for (String t : traders.keySet()) {
            if (t == name) {
                return -3;
            }
        }
        if (name.length() > 3 && name.length() < 11) {
            return -1; 
        } else if (password.length() > 1 && password.length() < 11) {
            return -2;
        } else {
            traders.put( name, new Trader(this, name, password));
            return 0; 
        }
    }
    
    public int login(String name, String password) {
        for (Trader t : traders.values()) {
            if (t.getName() == name) {
                if (t.getPassword() == password) {
                    if (loggedTraders.contains( t )) {
                        return -3;
                    } else {
                        loggedTraders.add( t );
                        t.openWindow();
                        String msg = "Welcome to SafeTrade!";
                        t.receiveMessage(msg);
                        return 0;
                    }
                } else {
                    return -2;
                }
                
            } else {
                return -1;
            }
        }
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
