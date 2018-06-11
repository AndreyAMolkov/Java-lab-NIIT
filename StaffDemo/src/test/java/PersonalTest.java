import org.junit.Assert;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class PersonalTest extends EmployeeTest  {



    @Test
    void getBase() {
        Personal Personal=new Personal("Abram","324234",100);
        int actual=Personal.getBase();
        Assert.assertEquals(100,actual);
    }

    @Test
    void setWorkTime() {
        Personal Personal=new Personal("Abram","324234",100);
        Personal.setCalculatedWorkTime(180);
        double actual=Personal.getWorkTime();
        Assert.assertEquals(180,actual,0.01);
    }

    @Test
    void getBase1() {
        Personal Personal=new Personal("Abram","324234",100);
        Personal.setCalculatedWorkTime(180);
        int actual=Personal.getBase();
        Assert.assertEquals(100,actual);
    }

    @Test
    void setBonusAfterPayment() {
        Personal Personal=new Personal("Abram","324234",100);
        Personal.setCalculatedWorkTime(180);
        Personal.setBonus(123);
        int actual=Personal.getBonus();
        Assert.assertEquals(123,actual);
    }

    @Test
    void setCalculatedWorkTime() {
        Personal Personal=new Personal("Abram","324234",100);
        Personal.setCalculatedWorkTime(180);
        int actual=Personal.getPayment();
        Assert.assertEquals(18000,actual);
    }
}