import org.junit.Assert;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ProgrammerTest {

    @Test
    void setBonusProject() {
        Programmer Personal=new Programmer("Abram","324234",100,180);
        Personal.setBonusProject(100);
        Assert.assertEquals(18000+100,Personal.getPayment());
    }
}