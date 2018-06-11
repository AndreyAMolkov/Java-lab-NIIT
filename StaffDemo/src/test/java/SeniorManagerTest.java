import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SeniorManagerTest {
    String path="/Staff of company.xlsx";
    Payroll Payroll;
    ArrayList<WorkProject> workProjects;
    SeniorManager  seniorManager;

    @BeforeEach
    void setUp() throws IOException {
        Payroll=new Payroll(path);
        this.workProjects=new ArrayList <>(0);
        this.workProjects=Payroll.getWorkProjects();

        this.seniorManager=workProjects.get(0).getSeniorManager();
    }
    @Test
    void setBonusOfProjects() {
    seniorManager.setBonusOfProjects(100) ;
        Assert.assertEquals(100,seniorManager.getBonus());
    }


    @Test
    void getSubordinates() {
        Assert.assertEquals(25,seniorManager.getNumberSubordinates());
        Assert.assertEquals(25,seniorManager.getSubordinates().size());

    }
}