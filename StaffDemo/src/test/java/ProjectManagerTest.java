
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ProjectManagerTest {
    ProjectManager Personal;
    @BeforeEach
    void setUp() {
        Personal=new ProjectManager("Abram","324234");
        Personal.setBonusProject(100);

    }

    @Test
    void setHeadingBonus() {

        Personal.setHeadingBonus(4) ;

        Assert.assertEquals(5000*4,Personal.getHeadingBonus());
        Assert.assertEquals(50_000+100+5000*4,Personal.getPayment());
    }
}