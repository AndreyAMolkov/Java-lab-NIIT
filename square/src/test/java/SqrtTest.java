import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SqrtTest {

    @Test
    void calc() {
        double val=2.0;
        String precision="0.001";
        Sqrt sqrt=new Sqrt(val,Double.parseDouble(precision));
        double result=sqrt.calc();
        assertEquals(1.414,result,0.001);
    }
}