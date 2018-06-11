
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


class EmployeeTest {

    @Test
    void getPayment(){
        Employee Employee=new Employee("AS", "42");
        Assert.assertEquals(0,Employee.getPayment());

    }
    @Test
    void getId(){
        Employee Employee=new Employee("AS","42");
        Assert.assertEquals("42",Employee.getId());

    }
    @Test
    void getName(){
        Employee Employee=new Employee("AS","42");
        Assert.assertEquals("AS",Employee.getName());
    }
@Test
    void  setProject(){
    Employee Employee=new Employee("AS","42");
    Employee.setProject(new WorkProject("Dark",1000,new ArrayList <WorkProject>(2)));
    String actual=Employee.getProject().get(0).getName();
    Assert.assertEquals("Dark",actual);
    Assert.assertEquals(1000,Employee.getProject().get(0).getCostProject());
}
    @Test
    void setBonus(){
        Employee Employee=new Employee("AS","42");
        Employee.setBonus(100);
        Assert.assertEquals(100,Employee.getBonus());
    }
}