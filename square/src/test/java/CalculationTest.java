import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



class CalculationTest {

    @Test
    void barrier() {
        Calculation Calculation=new Calculation();

        int costBarrier = 2000;// rub
        double actual=Calculation.barrier(2,costBarrier);
        Assertions.assertEquals(25132,actual,1);


    }

    @Test
    void path() {
        Calculation Calculation=new Calculation();
        int costPath=1000;
        double actual=Calculation.path(2,1,1000);
        Assertions.assertEquals(15708,actual,1);

    }
}