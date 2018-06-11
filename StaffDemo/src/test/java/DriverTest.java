import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DriverTest {

    @Test
    void setCalculatedWorkTime() {
        Driver Personal=new Driver("Abram","324234",100,180);

        int expected=18000+18000*20/100;
        assertEquals(expected,Personal.getPayment());
    }
}