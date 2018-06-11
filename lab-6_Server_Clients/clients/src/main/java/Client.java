
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class Client extends Thread{

    private Boolean checkSuccessfullyConnect;
    private String addressServer;
    private int socket;
    private Controls gui;
    private String marker;

    Client(String addressServer, int socket,Controls gui,String marker){

        this.checkSuccessfullyConnect=false;
        this.marker=marker;
        this.addressServer=addressServer;
        this.socket=socket;
        this.gui=gui;
        start();

    }
    @Override
    public void run(){

     gui.textFieldInfo.setText("Клиент стартовал");
        Socket server = null;
        checkSuccessfullyConnect=false;
        gui.textFieldInfo.setText("Соединяемся с сервером "+addressServer);
        BufferedReader in=null;
        PrintWriter out=null;
        BufferedReader inu=null;
        try {
            server = new Socket(addressServer,socket);
            in  = new BufferedReader( new InputStreamReader(server.getInputStream()));
            out = new PrintWriter(server.getOutputStream(),true);
            inu = new BufferedReader(new InputStreamReader(System.in));

        String fserver;


        //Основной цикл отправки сообщений серверу
        gui.textFieldInfo.setText("Соединие успешно");
        this.checkSuccessfullyConnect=true;

            out.println(marker);
           while ((fserver = in.readLine())!=null&&gui!=null){

            if(marker.equals("time")) {
               for(;;) {
                   try {
                       Thread.sleep(1000);

                       out.println(marker);
                       fserver = in.readLine();
                           gui.populateDateTime(fserver);

                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }

            }
            if(marker.equals("subscribe")) {

                           fserver = in.readLine();

                          gui.textAreaSubscribe.setText(fserver);


            }
            if(marker.equals("wisdom")) {

                gui.textAreaWisdom.setText(fserver);
            }
            if (marker.equalsIgnoreCase("exit")){
                break;
            }
               if(marker.equals("exchange")) {
                if(fserver.equals("Error")){
                    gui.textFieldErrorExchange.setVisible(true);
                    gui.textFieldErrorExchange.setText("Error of download data from: www.ecb.europa.eu");
                    gui.callServerExchange.setVisible(true);
                    gui.labelExchange.setText("exchange rate is not ready");

                }else{
                       ExchangeCalculate exchangeCalculate = new ExchangeCalculate(fserver);
                       gui.setExchangeCalculate(exchangeCalculate);
                    gui.textFieldErrorExchange.setVisible(false);

                   }
               };

        }
        } catch (IOException e) {
           gui.textFieldInfo.setText("Server not found");
          //  e.printStackTrace();
            Thread.interrupted();
        }
        try {
            out.close();
            in.close();
            inu.close();
            server.close();
        //    gui.textFieldInfo.setText("Соединие разорвано");
        }catch (IOException e){
            if(marker.equals("exchange"))
            gui.callServerExchange.setVisible(true);

            gui.textFieldInfo.setText(gui.textFieldInfo.getText()+", "+"connection is close");
        }catch (NullPointerException e1){
            // e.printStackTrace();
            gui.textFieldInfo.setText(gui.textFieldInfo.getText()+", "+"connection is close");
            if(marker.equals("exchange"))
                gui.callServerExchange.setVisible(true);
        }
       // Закрытие соединения и выход


    }



    public Boolean getCheckSuccessfullyConnect() {
        return checkSuccessfullyConnect; }
}

