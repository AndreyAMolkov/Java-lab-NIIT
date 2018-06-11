import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PayrollTest {
    String path="/Staff of company.xlsx";
    Payroll Payroll;
    ArrayList <ArrayList<String>> array;
    @BeforeEach
    void setUp() throws IOException {
        Payroll=new Payroll(path);

    }


    @Test
    void createMapArrayData()throws  IOException{
        int actual=Payroll.getArray().size();
        Assert.assertEquals(31,actual);// check delete
        Assert.assertEquals(4,Payroll.getMap().get("Project"));
    }
    @Test
    void createStaff()throws  IOException {

        Assert.assertEquals(30,Payroll.getStaff().size());
    }

}
