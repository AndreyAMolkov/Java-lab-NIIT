import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CleanerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void setCalculatedWorkTime() {
        Cleaner Personal=new Cleaner("Abram","324234",10,180);
        Assert.assertEquals(1800,Personal.getPayment());
    }
}