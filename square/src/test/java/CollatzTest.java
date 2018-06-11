import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollatzTest {

    @BeforeEach
    void setUp() {
       Collatz Collatz= new Collatz();

    }

    @Test
    void calculation() {
        Collatz Collatz= new Collatz();
        Assertions.assertEquals(8,Collatz.calculation(3));
    }

    @Test
    void maxSequence() {
        Collatz Collatz= new Collatz();
        long actual=Collatz.maxSequence(3);
        Assertions.assertEquals(8,actual);

    }
}