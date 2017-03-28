import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.regex.*;

import org.junit.*;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;


/**
 * SafeTrade tests: TradeOrder PriceComparator Trader Brokerage StockExchange
 * Stock
 *
 * @author David Xu
 * @version 3/28/17
 * @period 5
 * @author Assignment: JM Chapter 19 - SafeTrade
 * 
 */
public class JUSafeTradeTest
{
    // --Test TradeOrder
    /**
     * TradeOrder tests: TradeOrderConstructor - constructs TradeOrder and then
     * compare toString TradeOrderGetTrader - compares value returned to
     * constructed value TradeOrderGetSymbol - compares value returned to
     * constructed value TradeOrderIsBuy - compares value returned to
     * constructed value TradeOrderIsSell - compares value returned to
     * constructed value TradeOrderIsMarket - compares value returned to
     * constructed value TradeOrderIsLimit - compares value returned to
     * constructed value TradeOrderGetShares - compares value returned to
     * constructed value TradeOrderGetPrice - compares value returned to
     * constructed value TradeOrderSubtractShares - subtracts known value &
     * compares result returned by getShares to expected value
     */
    private String symbol = "GGGL";

    private boolean buyOrder = true;

    private boolean marketOrder = true;

    private int numShares = 123;

    private int numToSubtract = 24;

    private double price = 123.45;


    @Test
    public void tradeOrderConstructor()
    {
        TradeOrder to = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );
        String toStr = to.toString();

        assertTrue( "<< Invalid TradeOrder Constructor >>",
            toStr.contains( "TradeOrder[Trader trader:null" )
                && toStr.contains( "java.lang.String symbol:" + symbol )
                && toStr.contains( "boolean buyOrder:" + buyOrder )
                && toStr.contains( "boolean marketOrder:" + marketOrder )
                && toStr.contains( "int numShares:" + numShares )
                && toStr.contains( "double price:" + price ) );
    }


    @Test
    public void TradeOrderToString()
    {
        TradeOrder to = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );
        assertNotNull( to.toString() );
    }


    @Test
    public void tradeOrderGetTrader()
    {
        TradeOrder to = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );
        assertNull( "<< TradeOrder: " + to.getTrader() + " should be null >>",
            to.getTrader() );
    }


    @Test
    public void tradeOrderGetSymbol()
    {
        TradeOrder to = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );
        assertEquals(
            "<< TradeOrder: " + to.getTrader() + " should be " + symbol + " >>",
            symbol,
            to.getSymbol() );
    }


    @Test
    public void tradeOrderIsBuy()
    {
        TradeOrder to = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );

        assertTrue(
            "<< TradeOrder: " + to.isBuy() + " should be " + buyOrder + " >>",
            to.isBuy() );
    }


    @Test
    public void tradeOrderIsSell()
    {
        TradeOrder to = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );
        assertFalse(
            "<< TradeOrder: " + to.isSell() + " should be " + !buyOrder + " >>",
            to.isSell() );
    }


    @Test
    public void tradeOrderIsMarket()
    {
        TradeOrder to = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );
        assertTrue( "<< TradeOrder: " + to.isMarket() + " should be "
            + marketOrder + " >>", to.isMarket() );
    }


    @Test
    public void tradeOrderIsLimit()
    {
        TradeOrder to = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );

        assertFalse( "<< TradeOrder: " + to.isLimit() + " should be "
            + !marketOrder + ">>", to.isLimit() );
    }


    @Test
    public void tradeOrderGetShares()
    {
        TradeOrder to = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );
        assertTrue(
            "<< TradeOrder: " + to.getShares() + " should be " + numShares
                + ">>",
            numShares == to.getShares()
                || ( numShares - numToSubtract ) == to.getShares() );
    }


    @Test
    public void tradeOrderGetPrice()
    {
        TradeOrder to = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );
        assertEquals(
            "<< TradeOrder: " + to.getPrice() + " should be " + price + ">>",
            price,
            to.getPrice(),
            0.0 );
    }


    @Test
    public void tradeOrderSubtractShares()
    {
        TradeOrder to = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );
        to.subtractShares( numToSubtract );
        assertEquals(
            "<< TradeOrder: subtractShares(" + numToSubtract + ") should be "
                + ( numShares - numToSubtract ) + ">>",
            numShares - numToSubtract,
            to.getShares() );
    }


    // --Test TraderWindow Stub
    @Test
    public void traderWindowConstructor()
    {
        TraderWindow tw = new TraderWindow( null );
        assertNotNull( tw );
    }


    @Test
    public void traderWindowShowMessage()
    {
        TraderWindow tw = new TraderWindow( null );
        assertNotNull( tw );
        tw.showMessage( null );
    }


    // --Test PriceComparator

    @Test
    public void priceComparatorConstructor1()
    {
        PriceComparator compare = new PriceComparator();
        assertNotNull( compare );
    }


    @Test
    public void priceComparatorConstructor2()
    {
        PriceComparator compare = new PriceComparator( false );
        assertNotNull( compare );
    }


    @Test
    public void priceComparatorCompare()
    {
        boolean limitOrder = false;
        PriceComparator pc = new PriceComparator();
        PriceComparator pc2 = new PriceComparator( false );
        double price2 = 234.56;
        TradeOrder order1 = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );
        TradeOrder order2 = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );
        TradeOrder order3 = new TradeOrder( null,
            symbol,
            buyOrder,
            limitOrder,
            numShares,
            price );
        TradeOrder order4 = new TradeOrder( null,
            symbol,
            buyOrder,
            limitOrder,
            numShares,
            price2 );

        assertEquals( "<< PriceComparator: compare(market, market) should be 0 >>",
            pc.compare( order1, order2 ),
            0 );

        assertEquals( "<< PriceComparator: compare(market, limit) should be -1 >>",
            pc.compare( order1, order3 ),
            -1 );

        assertEquals( "<< PriceComparator: compare(limit, market) should be 1 >>",
            pc.compare( order3, order1 ),
            1 );

        assertEquals( "<< PriceComparator(asc): compare(limit, limit) should be "
            + (int)( ( price - price2 ) * 100 ) + " >>",
            pc.compare( order3, order4 ),
            (int)( ( price - price2 ) * 100 ) );

        assertEquals( "<< PriceComparator(desc): compare(limit, limit) should be "
            + (int)( ( price2 - price ) * 100 ) + " >>",
            pc2.compare( order3, order4 ),
            (int)( ( price2 - price ) * 100 ) );

    }


    // --Test Trader

    @Test
    public void traderConstructor()
    {
        Trader test = new Trader( new Brokerage( new StockExchange() ),
            "testTrader",
            "1000" );
        assertNotNull( test );
    }


    @Test
    public void traderToString()
    {
        Trader test = new Trader( new Brokerage( new StockExchange() ),
            "testTrader",
            "1000" );
        assertNotNull( test.toString() );
    }


    @Test
    public void traderCompareTo()
    {
        Trader test1 = new Trader( new Brokerage( new StockExchange() ),
            "testTrader1",
            "2000" );
        Trader test2 = new Trader( new Brokerage( new StockExchange() ),
            "testTrader2",
            "2500" );
        assertEquals( "<< Trader: " + test1.getName() + " compareTo ( "
            + test2.getName() + " ) should be "
            + test1.getName().compareToIgnoreCase( test2.getName() ) + " >>",
            test1.compareTo( test2 ),
            -1 );
    }


    @Test
    public void traderQuit()
    {
        Brokerage testBk = new Brokerage( new StockExchange() );
        Trader test1 = new Trader( testBk, "testTrader1", "1000" );
        testBk.addUser( "testTrader1", "Pass" );
        testBk.login( "testTrader1", "pass" );
        test1.quit();
        assertEquals( testBk.getLoggedTraders().size(), 0 );
    }


    @Test
    public void traderGetQuote()
    {
        StockExchange testSe = new StockExchange();
        testSe.listStock( symbol, "Trader", price );
        Trader testTrader = new Trader( new Brokerage( testSe ),
            "testTrader",
            "1000" );
        testTrader.getQuote( symbol );
        assertTrue(
            "<< Trader: getQuote( " + symbol + " ) should be "
                + testTrader.mailbox().peek() + " >>",
            testTrader.hasMessages() );
    }


    @Test
    public void traderPlaceOrder()
    {
        StockExchange testSe = new StockExchange();
        testSe.listStock( symbol, "Trader", price );
        Trader testTrader = new Trader( new Brokerage( testSe ),
            "testTrader",
            "1000" );
        testTrader.placeOrder( new TradeOrder( testTrader,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price ) );
        assertTrue(
            "<< Trader: placeOrder( " + symbol + " ) should be " + true + " >>",
            testTrader.hasMessages() );
    }


    @Test
    public void traderGetName()
    {
        Trader testTrader = new Trader( new Brokerage( new StockExchange() ),
            "testTrader",
            "1000" );
        assertEquals(
            "<< Trader: " + testTrader.getName() + " should be testTrader >>",
            testTrader.getName(),
            "testTrader" );
    }


    @Test
    public void traderGetPassword()
    {
        Trader testTrader = new Trader( new Brokerage( new StockExchange() ),
            "testTrader",
            "1000" );
        assertEquals(
            "<< Trader: " + testTrader.getPassword() + " should be 1000 >>",
            testTrader.getPassword(),
            "1000" );
    }


    @Test
    public void traderEquals()
    {
        Trader testTrader1 = new Trader( new Brokerage( new StockExchange() ),
            "testTrader1",
            "1000" );
        Trader testTrader2 = new Trader( new Brokerage( new StockExchange() ),
            "testTrader2",
            "2000" );
        assertFalse( "<< Trader: " + testTrader1.equals( testTrader2 )
            + " should be " + false + " >>",
            testTrader1.equals( testTrader2 ) );
    }


    @Test
    public void traderOpenWindow()
    {
        Trader testTrader1 = new Trader( new Brokerage( new StockExchange() ),
            "testTrader",
            "1000" );
        testTrader1.receiveMessage( "Test Message" );
        testTrader1.openWindow();
        assertFalse( "<< Trader: " + testTrader1.mailbox().size()
            + " should be " + 0 + " >>", testTrader1.hasMessages() );
    }


    @Test
    public void traderHasMessages()
    {
        Trader testTrader1 = new Trader( new Brokerage( new StockExchange() ),
            "testTrader1",
            "1000" );
        assertTrue( "<< Trader: " + testTrader1.mailbox().size() + " should be "
            + 0 + " >>", testTrader1.mailbox().isEmpty() );
    }


    @Test
    public void traderReceiveMessage()
    {
        Trader testTrader1 = new Trader( new Brokerage( new StockExchange() ),
            "testTrader1",
            "1000" );
        testTrader1.receiveMessage( "Test Message" );
        assertEquals(
            "<< Trader: " + testTrader1.mailbox().peek() + " should be "
                + "Test Message" + " >>",
            testTrader1.mailbox().remove(),
            "Test Message" );
    }
    // --Test Brokerage


    @Test
    public void brokerageConstructor()

    {

        StockExchange exchange = new StockExchange();

        exchange.listStock( "GGGL", "Giggle.com", 10.00 );

        Brokerage b = new Brokerage( exchange );

        String str = b.toString();

        assertTrue( "<< Invalid Brokerage Constructor >>",

        str.contains( "Brokerage[" ) );

    }

    @Test
    public void brokerageToString()
    {
        StockExchange test1 = new StockExchange();
        test1.listStock( "GGGL", "Giggle.com", 10.00 );
        Brokerage testBroker = new Brokerage( test1 );
        assertNotNull( testBroker.toString() );
    }


    // HELP MEH
    @Test
    public void brokerageAddUser()

    {

        StockExchange exchange = new StockExchange();

        exchange.listStock( "GGGL", "Giggle.com", 10.00 );

        Brokerage b = new Brokerage( exchange );

        assertTrue( "<< invalid name >>", b.addUser( "bob", "pass" ) == -1 );

        assertTrue( "<< invalid name >>",
            b.addUser( "traderUsers", "pass" ) == -1 );

        assertTrue( "<< invalid name >>", b.addUser( "java", "j" ) == -2 );

        assertTrue( "<< invalid name >>",
            b.addUser( "java", "traderUsers" ) == -2 );

        assertTrue( "<< invalid name >>", b.addUser( "java", "trader" ) == 0
            && b.getTraders().containsKey( "java" ) );

        assertTrue( "<< invalid name >>", b.addUser( "java", "user" ) == -3 );

    }


    @Test
    public void brokerageGetQuote()

    {

        StockExchange test1 = new StockExchange();
        test1.listStock( "GGGL", "Giggle.com", 10.00 );
        Brokerage test2 = new Brokerage( test1 );
        Trader testTrader = new Trader( test2, "name", "password" );
        test2.getQuote( symbol, testTrader );
        assertTrue( "<< Invalid Brokerage getQuote >>",
            testTrader.hasMessages() );

    }


    @Test
    public void brokerageLogin()

    {
        StockExchange testSe = new StockExchange();
        testSe.listStock( "GGGL", "Giggle.com", 10.00 );
        Brokerage testBroker = new Brokerage( testSe );

        String n = "java";
        String p = "trader";
        testBroker.addUser( n, p );
        assertTrue( "<< Invalid brokerage login >>",
            testBroker.login( "user", p ) == -1 );
        assertTrue( "<< Invalid brokerage login >>",
            testBroker.login( n, "Invalid" ) == -2 );
        assertTrue( "<< Invalid brokerage login >>",
            testBroker.login( n, p ) == 0
                && testBroker.getLoggedTraders()
                    .contains( testBroker.getTraders().get( n ) )
                && !testBroker.getTraders().get( n ).hasMessages() );

        testBroker.login( n, p );

        assertTrue( "<< invalid brokerage login >>",
            testBroker.login( n, p ) == -3 );

    }


    @Test
    public void brokerageLogout()

    {
        StockExchange testSe = new StockExchange();
        testSe.listStock( "GGGL", "Giggle.com", 10.00 );
        Brokerage testBroker = new Brokerage( testSe );

        testBroker.addUser( "java", "user" );
        testBroker.login( "java", "user" );
        testBroker.logout( testBroker.getTraders().get( "java" ) );
        assertFalse( "<< Invalid brokerage logout >>",
            testBroker.getLoggedTraders()
                .contains( testBroker.getTraders().get( "java" ) ) );

    }


    @Test
    public void brokeragePlaceOrder()

    {
        StockExchange testSe = new StockExchange();
        testSe.listStock( "GGGL", "Giggle.com", 10.00 );
        Brokerage testBroker = new Brokerage( testSe );
        Trader trader = new Trader( testBroker, "name", "password" );

        TradeOrder order = new TradeOrder( trader,
            symbol,
            true,
            false,
            10,
            10.0 );
        testBroker.placeOrder( order );
        assertTrue( "<< Invalid Brokerage getQuote >>", trader.hasMessages() );
        trader.openWindow();
        testBroker.placeOrder( order );
        assertFalse( "<< Invalid Brokerage getQuote >>", trader.hasMessages() );

    }


    // --Test StockExchange
    @Test
    public void stockExchangeConstructor()
    {
        StockExchange testSE = new StockExchange();
        String testSTR = testSE.toString();
        assertTrue( "<<Invalid StockExchange Constructor >>",
            testSTR.contains( "StockExchange[" ) );
    }


    @Test
    public void stockExchangeToString()
    {
        StockExchange testSE = new StockExchange();
        assertNotNull( testSE.toString() );
    }


    @Test
    public void stockExchangeListStock()
    {
        StockExchange testSE = new StockExchange();
        testSE.listStock( "GGGL", "Giggle.com", 10.0 );
        Map<String, Stock> testStock = testSE.getListedStocks();
        assertTrue( "<< Invalid StockExchange listStock >>",
            testStock.containsKey( "GGGL" ) );
    }


    @Test
    public void stockExchangeGetQuote()
    {
        StockExchange testSE = new StockExchange();
        testSE.listStock( "GGGL", "Giggle.com", 10.0 );
        String quote = testSE.getQuote( "GGGL" );
        assertTrue( "<< Invalid StockExchange getQuote >>", quote != null );
    }


    @Test
    public void stockExchangePlaceOrder()

    {

        StockExchange to = new StockExchange();

        Brokerage safeTrade = new Brokerage( to );

        safeTrade.addUser( "stockman", "sesame" );

        Trader trade = new Trader( safeTrade, "stockman", "sesame" );

        TradeOrder order = new TradeOrder( trade,

            symbol,

            buyOrder,

            marketOrder,

            numShares,

            price );

        to.listStock( "GGGL", "Giggle.com", 10.00 );

        to.placeOrder( order );

        assertTrue( "<< Invalid StockExchange placeOrder >>",
            trade.hasMessages() );

        trade.openWindow();

        to.placeOrder( order );

        assertFalse( "<< Invalid StockExchange placeOrder >>",
            trade.hasMessages() );

    }
    // --Test Stock

    private String name = "Giggle.com";


    @Test
    public void stockConstructor()
    {
        PriorityQueue<TradeOrder> sellOrders = new PriorityQueue<TradeOrder>(
            10, new PriceComparator() );
        PriorityQueue<TradeOrder> buyOrders = new PriorityQueue<TradeOrder>( 10,
            new PriceComparator( false ) );
        int volume = 0;
        Stock s = new Stock( symbol, name, price );

        String toStr = s.toString();

        assertTrue( "<< Invalid Stock Constructor >>",
            toStr.contains( "String stockSymbol:" + symbol )
                && toStr.contains( "String companyName:" + name )
                && toStr.contains( "double loPrice:" + price )
                && toStr.contains( "double hiPrice:" + price )
                && toStr.contains( "double lastPrice:" + price )
                && toStr.contains( "int volume:" + volume )
                && toStr.contains( "PriorityQueue buyOrders:" + buyOrders )
                && toStr.contains( "PriorityQueue sellOrders:" + sellOrders ) );
    }


    @Test
    public void StockGetQuote()

    {

        Stock stock = new Stock( symbol, "name", price );

        assertEquals( "MESSAGE", stock.getQuote(), "name (" + symbol + 
            ")\nPrice: " + price + " hi: " + price + " lo: " + price
            + " vol: 0\nAsk: none Bid: none" );

    }


    @Test
    public void StockPlaceOrder()
    {
        StockExchange testSE = new StockExchange();
        Stock testS = new Stock( symbol, "name", price );
        testSE.listStock( symbol, "name", price );
        Brokerage testBroker = new Brokerage( testSE );
        Trader testTrader = new Trader( testBroker, "name", "password" );
        TradeOrder testOrder = new TradeOrder( testTrader,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );
        testS.placeOrder( testOrder );
        assertEquals( testS.getBuyOrders().isEmpty(), false );
        assertTrue( testTrader.hasMessages() );
    }


    @Test
    public void stockToStringTest()
    {
        Stock testS = new Stock( symbol, name, price );
        assertNotNull( testS.toString() );
    }

    // Remove block comment below to run JUnit test in console
    /*
     * public static junit.framework.Test suite() { return new
     * JUnit4TestAdapter( JUSafeTradeTest.class ); }
     * 
     * public static void main( String args[] ) {
     * org.junit.runner.JUnitCore.main( "JUSafeTradeTest" ); }
     */
}
