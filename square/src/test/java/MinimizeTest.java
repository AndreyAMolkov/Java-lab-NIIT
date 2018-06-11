import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinimizeTest {


    @Test
    void numbersToDash() {
        String line="1,2,3,4,6,7,12,34,56,57,58";
        String[] buf = line.split(",");
       Minimize Min=new Minimize(buf,",");
       String actual=Min.CreateLine();
       assertEquals("1-4,6-7,12,34,56-58.",actual);

        // assertEquals();

    }
}