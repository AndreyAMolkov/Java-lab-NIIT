import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class bigNumbersTest {
   /* private final ByteArrayOutputStream outStream=new ByteArrayOutputStream();
    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(null);
    }
*/
    @Test
    void create() {
        BigNumbers BigNumbers=new BigNumbers("78");
        String []result=BigNumbers.create();
        String[] test = {
                " *****  *** ",
                "     * *   *",
                "    *  *   *",
                "   *    *** ",
                "  *    *   *",
                " *     *   *",
                " *      *** "
        };

        Assertions.assertArrayEquals(test,result);
    }



}