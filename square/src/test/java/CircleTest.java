import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircleTest {

    @Test
    void setFerence() {
        Circle circle=new Circle();
        circle.setRadius(2);
        double actual=circle.getFerence();
        Assertions.assertEquals(12.56637,actual,0.0001);
    }

    @Test
    void setRadius() {
        Circle circle=new Circle();
        circle.setFerence(12.56);
        double actual=circle.getRadius();
        Assertions.assertEquals(2,actual,0.01);

    }

    @Test
    void setArea() {
        Circle circle=new Circle();
        circle.setRadius(2);
        double actual=circle.getArea();
        Assertions.assertEquals(12.56,actual,0.01);

    }
}