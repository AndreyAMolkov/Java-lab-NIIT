import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class WisdomDemoTest {
    WisdomDemo wisdomDemo;
    @Before
    public void setUp() throws Exception {
       wisdomDemo=new WisdomDemo("/wisdom.TXT");
    }

    @Test
    public void getArrayListWisdom() {
        ArrayList<String>actual;
       actual= wisdomDemo.getArrayListWisdom();
        Assert.assertEquals(18,actual.size());
    }
    @Test
    public void getRandomWisdom() {
String actual=null;
        for(int i=0;i<30;i++) {
            actual= wisdomDemo.getRandomWisdom();
            Assert.assertNotEquals("true if random string != null", null,actual);
        }
    }
}