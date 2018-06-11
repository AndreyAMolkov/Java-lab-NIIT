import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExchangeCalculateTest {
String line="USD 1.1737,JPY 128.44,BGN 1.9558,CZK 25.696,DKK 7.4434,GBP 0.87673,HUF 318.64,PLN 4.2848,RON 4.6548," +
        "SEK 10.2583,CHF 1.1546,ISK 122.50,NOK 9.5030,HRK 7.3790,RUB 72.6626,TRY 5.4193,AUD 1.5311," +
        "BRL 4.3893,CAD 1.5148,CNY 7.5166,HKD 9.2091,IDR 16281.57,ILS 4.1836,INR 78.7120," +
        "KRW 1254.88,MXN 23.3661,MYR 4.6655,NZD 1.6654,PHP 61.647,SGD 1.5655,THB 37.511,ZAR 14.7053";

    @Test
    public void getCalculateExchangePair() {
        ExchangeCalculate exchangeCalculate=new ExchangeCalculate(line);
        String actual=exchangeCalculate.getCalculateExchangePair("RUB",100,"USD");
        Assert.assertEquals(6190.90,Double.parseDouble(actual),0.001);
    }
}