import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class Controls extends Application {
private Controls controls;
static ArrayList<Client> listClient=new ArrayList <>(0);
ExchangeCalculate exchangeCalculate;
private Thread threadClock;
  // private Thread secThread;
   private String hourCurrent;
   private String minCurrent;
   private String dataCurrent;
    @FXML
    CheckBox checkBoxSubscribe;
    @FXML
    TextField textFieldErrorExchange;
    @FXML
    Button callServerSubscribe;
    @FXML
    TextArea textAreaSubscribe;
    @FXML
    TextArea textAreaExchange1;
    @FXML
    TextArea textAreaExchange2;
    @FXML
    ComboBox<String> comboBoxExchange1;
    @FXML
    ComboBox<String> comboBoxExchange2;
    @FXML
    Button callServerExchange;
    @FXML
    Button callServerWisdom;
    @FXML
    TextArea textAreaWisdom;
    @FXML
    TextField textFieldDate;
    @FXML
    TextField textFieldInfo;
    @FXML
    TextField textFieldCountThreadClient;
    @FXML
    Pane pane;
    @FXML
    TextField textFieldHour;
    @FXML
    TextField textFieldMin;
    @FXML
    TextField textFieldSec;
    @FXML
    Label labelExchange;
    @FXML
    TextArea textAreaServer;
    @FXML
    TextArea textAreaSocket;
    @FXML
    Button buttonStopClock;
    @FXML
    Button callServerTime;
    @FXML
    public void initialize(){
        textAreaSubscribe.setVisible(false);
        checkBoxSubscribe.selectedProperty().setValue(true);
        checkBoxSubscribe.setVisible(false);
        textFieldErrorExchange.setVisible(false);
        textAreaServer.setText("localhost");
        textAreaSocket.setText(Integer.toString(1234));
        textAreaExchange2.setEditable(true);
        exchangeCalculate=null;
        threadClock=null;

        buttonStopClock.setVisible(false);
        this.dataCurrent=" ";
        this.hourCurrent=" ";
        this.minCurrent=" ";
        //marker for sec
        /*
        Runnable s = () -> {
            for (; ; ) {
                try {
                    Thread.sleep(1000);
                    textFieldSec.setText(" ");
                    Thread.sleep(1000);
                    textFieldSec.setText(":");
                } catch(InterruptedException e){
                   // e.printStackTrace();
                    textFieldInfo.setText("Clock is interrupted");
                }
            }
        };
       secThread = new Thread(s);
       secThread.setDaemon(true);
        this.secThread=secThread;
        */

    }

    public void onClickSubscribe(){
        Client buf=new Client(textAreaServer.getText(), Integer.parseInt(textAreaSocket.getText()),this,"subscribe");
        listClient.add(buf);

            checkBoxSubscribe.setVisible(true);
            callServerSubscribe.setVisible(false);
            textAreaSubscribe.setVisible(true);

    }
    public void onClickCheck(){
        checkBoxSubscribe.selectedProperty().setValue(true);
    }
    public void onClickStopClock(){
        if(threadClock!=null) {
            threadClock.interrupt();
          //  if(secThread!=null&&!secThread.isInterrupted())
           //     secThread.interrupt();
            callServerTime.setVisible(true);
            buttonStopClock.setVisible(false);
        }
    }
    public void onClickCallServerExchange(){

        listClient.add(new Client(textAreaServer.getText(), Integer.parseInt(textAreaSocket.getText()),this,"exchange"));
        textFieldCountThreadClient.setText(Integer.toString(listClient.size()));
        callServerExchange.setVisible(false);
        textFieldErrorExchange.setVisible(false);
        labelExchange.setText("exchange rate is ready");


    }
    public void onClickCallServerWisdom() {
      // Client client=new Client(textAreaServer.getText(), Integer.parseInt(textAreaSocket.getText()),this,"wisdom");
    listClient.add(new Client(textAreaServer.getText(), Integer.parseInt(textAreaSocket.getText()),this,"wisdom"));
    textFieldCountThreadClient.setText(Integer.toString(listClient.size()));
    }

    public void onClickCallServerTime()  {

    this.controls=this;
     listClient.add(new Client(textAreaServer.getText(), Integer.parseInt(textAreaSocket.getText()), controls, "time"));
      if(threadClock==null) {
                Runnable client =()-> listClient.get(listClient.size()-1);

          Thread clTime = new Thread(client);
          clTime.start();
         // secThread.start();
          textFieldCountThreadClient.setText(Integer.toString(listClient.size()));
         // buttonStopClock.setVisible(true);
          this.threadClock = clTime;
          callServerTime.setVisible(false);


          if (textFieldInfo.equals("Server not found,connection is close")) {
              threadClock=null;
             // secThread.interrupt();
              if (clTime.isInterrupted())
                  callServerTime.setVisible(true);
          }
      }else if(threadClock.isInterrupted()){
          threadClock.start();
         // secThread.start();
      }

    }

    public void setExchangeCalculate(ExchangeCalculate exchangeCalculate) {
        this.exchangeCalculate = exchangeCalculate;
        List<String> list = new ArrayList<String>();
        list.addAll(exchangeCalculate.getNameOfCurrency());
        ObservableList<String>observableList = FXCollections.observableList(list);

        comboBoxExchange1.setItems(observableList);
        comboBoxExchange2.setItems(observableList);

    }
    @FXML
    private void onWrite(){
        if(exchangeCalculate!=null && comboBoxExchange1.getValue()!=null
                &&comboBoxExchange2.getValue()!=null&&!textAreaExchange1.getText().isEmpty()
                &&!comboBoxExchange1.getValue().equals(comboBoxExchange2.getValue())) {
            String currencyTo = comboBoxExchange2.getValue();
            String currencyFrom = comboBoxExchange1.getValue();
            double amountCurrency = Double.parseDouble(textAreaExchange1.getText());
            textAreaExchange2.setText(exchangeCalculate.getCalculateExchangePair(currencyTo, amountCurrency, currencyFrom));
        }
}

    public static void main(String[] args) {

        launch(args);
    }
public Controls getGUI(){
        return this;
}
    public void populateDateTime(String inputDateTime){

        DateTimeSeparate dateTime=new DateTimeSeparate(inputDateTime);

                if (!dataCurrent.equals(dateTime.getDate())){
                    textFieldDate.setText(dateTime.getDate());
                    this.dataCurrent=(dateTime.getDate());
                }

                if(!hourCurrent.equals(dateTime.getHour())) {
                    textFieldHour.setText(dateTime.getHour());
                    this.hourCurrent=dateTime.getHour();
                }

                if (!minCurrent.equals(dateTime.getMin())) {
                    textFieldMin.setText(dateTime.getMin());
                this.minCurrent=dateTime.getMin();
                }


               textFieldSec.setText(dateTime.getSec());


    }
    @Override
    public void start(Stage primaryStage) {


        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/DigitClock.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("java-lab-6");
        primaryStage.setScene(new Scene(root, 600, 800));
        primaryStage.show();
    }

}

