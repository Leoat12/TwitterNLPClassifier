package br.unirio;

import java.util.ArrayList;

import br.unirio.utils.TweetTreatment;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        ArrayList<String> results = TweetTreatment.extractLocation("sinfonia\tO\r\ndos\tO\r\ndomingos\tO\r\nem\tO\r\nIpanema\tB-LOCATION\r\n\r\n");
        assertEquals("Ipanema", results.get(0));

        results = TweetTreatment.extractLocation("Cachorro\tO\r\n7\tO\r\npele\tO\r\ndo\tO\r\ncarraiii\tO\r\nem\tO\r\nComplexo\tB-LOCATION\r\nDa\tI-LOCATION\r\nPenha\tI-LOCATION\r\n\r\n");
        assertEquals("Complexo Da Penha", results.get(0));
    }
}
