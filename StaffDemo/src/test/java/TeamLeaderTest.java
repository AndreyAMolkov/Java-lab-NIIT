import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TeamLeaderTest {
    String path="/Staff of company.xlsx";
    Payroll Payroll;
    ArrayList <ArrayList<String>> arrayInput;
    ArrayList<WorkProject> workProjects;
    TeamLeader teamLeader;

    @BeforeEach
    void setUp() throws IOException {
        Payroll=new Payroll(path);
        this.workProjects=new ArrayList <>(0);
        this.workProjects=Payroll.getWorkProjects();

        this.teamLeader=workProjects.get(0).getTeamLeader();
    }


    @Test
    void getEngineers() {

    Assert.assertEquals(11,teamLeader.getEngineers().size());
    }

    @Test
    void getRateFromManagingOfTeam() {
        Assert.assertEquals(5000,teamLeader.getRateFromManagingOfTeam());
    }

    @Test
    void getHeadingBonus() {
        Assert.assertEquals(11*5000,teamLeader.getHeadingBonus());
    }

    @Test
    void setHeadingBonus() {
        teamLeader.setHeadingBonus(5);
        Assert.assertEquals(5*5000,teamLeader.getHeadingBonus());
    }
}