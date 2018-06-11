import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AutomataTest {

    @Test
    public void getMenu() {
        Automata Automata=new Automata();
        String actual=Automata.getMenu()[0][0];
        assertEquals("Coffee black", actual);
    }

    @Test
    public void getPrice() {
        Automata Automata=new Automata();
        int actual=Automata.getPrice(1);
        assertEquals(10, actual);
    }

    @Test
    public void getState() {
        Automata Automata=new Automata();
        Automata.on();
        Automata.off();
        String actual=Automata.getState().toString();
        assertEquals("OFF", actual);
    }

    @Test
    public void on() {
            Automata Automata=new Automata();
            Automata.on();
            String actual=Automata.getState().toString();
            assertEquals("WAIT", actual);
    }

    @Test
    public void off() {
        Automata Automata=new Automata();
        String actual=Automata.getState().toString();
        assertEquals("OFF", actual);
    }

    @Test
    public void coin() {
        Automata Automata=new Automata();
        //AutomataGUIDemo A=new AutomataGUIDemo( Automata);
        Automata.on();
        Automata.coin(20);
        Automata.coin(2);
        int actual=Automata.getCashUser();
        assertEquals(22, actual);
    }
// checking the sale of the drink and crediting money to revenue
    @Test
    public void choice() {
        Automata Automata=new Automata();
       // AutomataGUIDemo A=new AutomataGUIDemo(Automata);
        Automata.on();
        Automata.coin(40);
        Automata.choice(1);//the choice of drink 10
        int actual=Automata.getCashRevenue();// the money received by the vending machine with drinks
        int expected=Automata.getPrice(1);
        assertEquals(expected, actual);

    }

    @Test
    public void cancel() {
        Automata Automata=new Automata();
        //AutomataGUIDemo A=new AutomataGUIDemo(Automata);
        Automata.on();
        Automata.coin(20);
        Automata.cancel();
        int actual=Automata.getCashUser();
        assertEquals(0, actual);

    }
}