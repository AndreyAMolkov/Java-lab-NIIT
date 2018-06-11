import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ExchangeRateTest {

    @Test
    public void getLineExchangeRate() throws IOException {
        ExchangeRate exchangeRate=new ExchangeRate();
        String line=exchangeRate.getLineExchangeRate();
        Assert.assertTrue(line!=null);
        Assert.assertTrue(line.contains(","));// symbol separate
        Assert.assertTrue(line.contains(" "));// symbol separate
        Assert.assertFalse(line.lastIndexOf(",")==line.length()-1);
    }
}