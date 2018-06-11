import netscape.javascript.JSObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.util.HashMap;

import static com.sun.deploy.trace.Trace.flush;


public class AutomataDemo {
    public static void main(String[]args){

        final String addressMenu = "/menuPrice.TXT";

        Automata nescafe =new Automata();

        AutomataGUIDemo Frame=new AutomataGUIDemo(nescafe);
        ListenerButton l=new ListenerButton(nescafe,Frame);
        Frame.workPanel();

nescafe.on();


    }
}
class ListenerButton implements ActionListener{
       private AutomataGUIDemo gui;
       private HashMap<String, String>  data;
       private Automata Automata;
       private final String separate;
       private final String caseWord;
       private int positionParameter;
       Thread thrd;
    ListenerButton(Automata a,AutomataGUIDemo gui){
        this.gui=gui;
        this.Automata=a;
        this.separate=",";
        this.caseWord="product";
        this.positionParameter=2;// zero position - caseWord, first -name, second - parametr
        a.setListener(this);

    }
    void setDataToGUI(  HashMap<String, String>  data){

        this.data=data;
        setTextArea();
    }

    private void setTextArea(){
        gui.setTextArea(data);

    }
    public void actionPerformed(ActionEvent e) {

        String line=e.getActionCommand();
        //System.out.println(line);
        String[]buf= line.split(separate);
        switch (buf[0]) {
            case "product":
                caseWord(buf[positionParameter]);
                break;
            case "cash":
                caseCash(buf[positionParameter]);
             break;
            case "service":
               if(buf[positionParameter].equals("Cancel"))
                    caseCancel();
               break;
                default:
                   // System.out.println("Error switch caseWord");
        }

    }
   private void caseWord(String parameter){

       Automata.choice(Integer.parseInt(parameter));
   }
    private void caseCash(String parameter){

        Automata.coin(Integer.parseInt(parameter));
    }
    private void caseCancel(){

        Automata.cancel();

    }
}

class Automata {
    private ListenerButton listener;
    private boolean productErrorMaxPosition=false;
    private int cashRevenue;
    private int cashUser;
    private Integer cashNeedAdd;
    private String[][] menu;
    private String productName;
    private int productPrice;
    private int cashBack;
    private int positionPriceOfProduct;
    enum STATES {OFF, WAIT, ACCEPT, CHECK, COOK}    ;
    private STATES state;
    HashMap<String, String>  data;
   private int marker;
    HashMap<String, String>  setData(String line){
       HashMap<String, String> data = new HashMap<String, String>();

                    data.put("cash", "Your cash is "+getCashUser());
                    data.put("text", line);
            this.data=data;
            return data;
   }

    String [][] getMenu(){
	return menu;
}
		Automata() {
        this.marker='#';
        this.state = STATES.OFF;
        this.cashRevenue = 0;
        this.cashUser = 0;
        this.cashBack = 0;
        this.cashNeedAdd = 0;
        //this.productPrice=1;
        setMenu("/menuPrice.TXT");
        this.positionPriceOfProduct=1;
    }

    String getNameMethod() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }
void setListener(ListenerButton l){
        this.listener=l;
}
    int getPrice(int productPosition) {
        return Integer.parseInt(menu[productPosition][positionPriceOfProduct]);
    }

    STATES getState() {
    	return state;
    }


    private void setMenu(String menuFile) {
        BufferedReader fin;
        int sizeArray=0;

        try {
            InputStream inputStream=System.class.getResourceAsStream(menuFile);
            fin=new BufferedReader(new InputStreamReader(inputStream));

            int ch=' ';
            for(ch = fin.read();ch!=-1;ch = fin.read())
                if(ch ==marker)
                    sizeArray++;

            fin=new BufferedReader(new InputStreamReader(System.class.getResourceAsStream(menuFile)));

            menu=new String[sizeArray][2];
            ch=' ';
            int i=0;

            do {
                ch = fin.read();
                if(ch==marker) {
                    menu[i][0]  = fin.readLine();//the name of product
                    menu[i][1] = fin.readLine();//the price of product
                    i++;
                }
            }while (ch!=-1);
        } catch (FileNotFoundException exc) {
            System.out.println("Error Input of the file: " + menuFile + " " + exc);
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    void on() {
if(menu!=null) {
    if (state == STATES.OFF)
        state = STATES.WAIT;
}else
    System.out.println("Error: don't download menu ");
    }

    void off() {
      //  if (state == STATES.WAIT)
            state = STATES.OFF;
    }

    void coin(int cashInput) {

        if (state == STATES.WAIT || state == STATES.ACCEPT) {
            cashUser += cashInput;
            state = STATES.ACCEPT;
        }

        print(getNameMethod());
    }

    void choice (int productPosition) {
      //  cashNeedAdd = 0;
        if (state == STATES.ACCEPT || cashUser > 0) {
            try {

                productName = menu[productPosition][0];
            } catch (ArrayIndexOutOfBoundsException exc) {
                productErrorMaxPosition = true;
                print(getNameMethod());
                productErrorMaxPosition = false;
                return;
            }


            productPrice = getPrice(productPosition);

            print(getNameMethod());
            check();
        }

    }

    private void check() {

        if (productPrice <= cashUser) {
                cook(productName);
        } else {
            cashNeedAdd = productPrice - cashUser;//the information only for print to the user
            productName = "";
            productPrice = 0;

        }

        print(getNameMethod());
        cashNeedAdd=0;
    }

    void cancel() {
        if (state == STATES.CHECK || state == STATES.COOK)
            state = STATES.ACCEPT;

        if (state == STATES.ACCEPT) {
            productPrice = 0;
            productName = "";
            cashNeedAdd = 0;

            if (cashUser > 0) {
                cashBack = cashUser;// if need, you can use the cashBack for return to user
                cashUser = 0;
                cashBackCancel();
            } else {
                state = STATES.WAIT;
            }
            print(getNameMethod());
        }

    }

    private void cashBackCancel() {
        print(getNameMethod());
        cashBack = 0;
        state = STATES.WAIT;
    }

    private void cook(String productName) {
        state = STATES.COOK;
        cashUser-=productPrice;
        cashRevenue += productPrice;
        print(getNameMethod());
        cancel();
        finish();
    }

    int getCashUser(){
        return cashUser;
    }

    int getCashRevenue(){
        return cashRevenue;
    }

    private void finish()  {
        state = STATES.WAIT;
        print(getNameMethod());

    }

    void print(String method) {
/*
        if(productErrorMaxPosition==true) {
            System.out.println("Attention: This product is not available for selection");
            return;
        }
            System.out.print(method + " - " + state + "  -  ");
 */
        if (state == STATES.ACCEPT && productPrice > 0) {
           // System.out.println("Your choice :" + productName + ", price = " + productPrice + " rub.");
            setData("Your choice :" + productName + ", price = " + productPrice + " rub.");
            try {
                listener.setDataToGUI(data);
            }catch (NullPointerException e){
                ;//empty . this create only for test
            }
            return;
        }
        if (state == STATES.ACCEPT && cashUser > 0 && productPrice == 0) {
            setData(" ");
            try {
                listener.setDataToGUI(data);
            }catch (NullPointerException e){
                ;//empty . this create only for test
            }
           // System.out.println("Your cash: " + cashUser);
            if (cashNeedAdd > 0) {
                System.out.println("Your cash is: " + cashUser + ", You need add " + cashNeedAdd + " rub");
                setData("Your cash is: " + cashUser + ", You need add " + cashNeedAdd + " rub");
                listener.setDataToGUI(data);
            }
            return;
        }
        if (state == STATES.ACCEPT && cashBack > 0) {

           // System.out.println("CashBack: " + cashBack);
            setData("CashBack: " + cashBack);
            try {
                listener.setDataToGUI(data);
            }catch (NullPointerException e){
                ;//empty . this create only for test
            }
            return;
        }
        if (state == STATES.COOK) {

           // System.out.println("Your  " + productName + " is cooking, wait please");
            setData("Your  " + productName + " is cooking, wait please");
            try {
                listener.setDataToGUI(data);
            }catch (NullPointerException e){
                ;//empty . this create only for test
            }
            try {
                Thread.sleep(2000);// time for cooking
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setData("Your " + productName + " is ready.");
            try {
                listener.setDataToGUI(data);
            }catch (NullPointerException e){
                ;//empty . this create only for test
            }
          //  System.out.println("Your " + productName + " is ready.");
            return;
        }


    }
}