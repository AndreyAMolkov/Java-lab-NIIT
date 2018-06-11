

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WorkProjectTest {

    String path="/Staff of company.xlsx";
    Payroll Payroll;
    ArrayList <ArrayList<String>> array;
    ArrayList<WorkProject>workProjects;
    int costOfPersonnel=0;
    @BeforeEach
    void setUp() throws IOException {
        Payroll=new Payroll(path);
        this.workProjects=Payroll.getWorkProjects();
        for(Employee employee:Payroll.getStaff()){
            String position=employee.getClass().getCanonicalName();
            if (position.equals("Cleaner")||position.equals("Driver"))
                this.costOfPersonnel+=employee.getPayment();
        }


    }


    @Test
    void calculateBonusOfProject() {
        int actual=0;

        for(WorkProject workProject:workProjects){
            workProject.calculateBonusOfProject(costOfPersonnel/workProjects.size());
        //assertEquals(0,workProject.getBalanceOfProject());
        }
        for(WorkProject workProject:workProjects){
            workProject.calculateBonusOfProjectForSeniorManager();

        }
        int payment=0;
        for (Employee employee:Payroll.getStaff()) {
            payment=employee.getPayment();
            actual += payment;
        }
        assertEquals(2_000_000,actual);
}


    @Test
    void getName() {
        Assert.assertEquals("Dark",workProjects.get(0).getName());
    }

    @Test
    void setManager() {
        Assert.assertEquals("Витюгов Кондрат Андреевич",workProjects.get(0).getManager().getName());
        String position;
        for(WorkProject workProject:workProjects) {
            Assert.assertNotEquals(null, workProject.getManager());
                position=workProject.getManager().getClass().getName();
                assertEquals("Manager",position);

        }
    }

    @Test
    void getCostProject() {
        for(WorkProject workProject:workProjects)
        Assert.assertEquals(1000_000,workProject.getCostProject());

    }

    @Test
    void getBalanceOfProject() {
        for(WorkProject workProject:workProjects)
        Assert.assertNotEquals(0,workProject.getBalanceOfProject());
    }

    @Test
    void setProgrammers() {
        String position;
        for(WorkProject workProject:workProjects) {
            Assert.assertNotEquals(0, workProject.getProgrammers().size());
           for(Programmer programmer:workProject.getProgrammers()){
            position=programmer.getClass().getName();
            assertEquals("Programmer",position);
           }

        }
    }

    @Test
    void setTesters() {
        String position;
        for(WorkProject workProject:workProjects) {
            Assert.assertNotEquals(0, workProject.getTesters().size());
            for(Tester tester:workProject.getTesters()){
                position=tester.getClass().getName();
                assertEquals("Tester",position);
            }

        }
    }

    @Test
    void setTeamLeader() {
        String position;
        for(WorkProject workProject:workProjects) {
            Assert.assertNotEquals(null, workProject.getTeamLeader());
                position=workProject.getTeamLeader().getClass().getName();
                assertEquals("TeamLeader",position);
        }
    }

    @Test
    void setProjectManager() {
        String position;
        for(WorkProject workProject:workProjects) {
            Assert.assertNotEquals(null, workProject.getProjectManager());
            position=workProject.getProjectManager().getClass().getName();
            assertEquals("ProjectManager",position);
        }
    }

    @Test
    void setSeniorManager() {
        String position;
        for(WorkProject workProject:workProjects) {
            Assert.assertNotEquals(null, workProject.getSeniorManager());
            position=workProject.getSeniorManager().getClass().getName();
            assertEquals("SeniorManager",position);
        }
    }
}