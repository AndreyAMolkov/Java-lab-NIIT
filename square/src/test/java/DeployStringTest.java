import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeployStringTest {

    @Test
    void createLine() {
        String line="1,2,3,4-7";
        String separate =",";
        String dash="-";
        String []buf=line.split(separate);
        DeployString Deploy=new DeployString(buf,separate,dash);
        String actual=Deploy.CreateLine();
        assertEquals("1,2,3,4,5,6,7.",actual);

    }

    @Test
    void dashToNumbers() {
        String line="4-7";
        String separate =",";
        String dash="-";
        String []buf=line.split(separate);
        DeployString Deploy=new DeployString(buf,separate,dash);
        String actual=Deploy.DashToNumbers(line);
        assertEquals("4,5,6,7",actual);
    }
}