import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InsertRopeTest {

    @Test
    void calculationGap() {
        InsertRope Rope=new InsertRope(1);
        double actual=Rope.CalculationGap(3);
        Assertions.assertEquals(0.159,actual,0.01);
    }
}