import org.junit.Assert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {
    String path="/Staff of company.xlsx";
    Payroll Payroll;
    ArrayList <ArrayList<String>> arrayInput;
    ArrayList<WorkProject> workProjects;
    Manager manager;

    @BeforeEach
    void setUp() throws IOException {
        Payroll=new Payroll(path);
        this.workProjects=new ArrayList <>(0);
        this.workProjects=Payroll.getWorkProjects();

        this.manager=workProjects.get(0).getManager();
    }

    @Test
    void setBonusProject() {
        Manager Personal=new Manager("Abram","324234");
        Personal.setBonusProject(100);
        Assert.assertEquals(30_000+100,Personal.getPayment());
    }
}