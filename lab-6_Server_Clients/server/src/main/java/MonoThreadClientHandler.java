import javafx.concurrent.Task;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

//Разработать сетевое приложение, в котором сервер по запросу клиента высылает значение даты/времени.
// Клиент, построенный на основе GUI отображает дату и время в виде цифровых часов.
public class MonoThreadClientHandler  implements Runnable {

GuiServer_Controls frame;
static Socket clientDialog;
    private String input;
    // поток для чтения данных
    private BufferedReader in = null;
    // поток для отправки данных
    private PrintWriter out = null;

    public MonoThreadClientHandler(Socket client,GuiServer_Controls frame) {
        MonoThreadClientHandler.clientDialog = client;
        this.frame=frame;
    }

    @Override
    public void run() {


        try {
            // инициируем каналы общения в сокете, для сервера
            // канал записи в сокет следует инициализировать сначала канал чтения для избежания блокировки выполнения программы на ожидании заголовка в сокете

            MonoThreadClientHandler MonoThreadClientHandler = new MonoThreadClientHandler(clientDialog, frame);


            // серверный сокет

// создаем потоки для связи с клиентом
            in = new BufferedReader(new InputStreamReader(clientDialog.getInputStream()));
                out = new PrintWriter(clientDialog.getOutputStream(), true);



// цикл ожидания сообщений от клиента
            frame.textAreaStatus.setText("Ожидаем сообщений");
            int countTime=0;
try {
    while (clientDialog != null) {
        if ((input = in.readLine()) != null) {
           if(!input.equalsIgnoreCase("time"))
            frame.textAreaRequest.appendText(clientDialog.getInetAddress().toString()
                    + " " + "получено -" + input.toString() + " ");
           else if(countTime==0) {
               frame.textAreaRequest.appendText(clientDialog.getInetAddress().toString()
                       + " " + "получено -" + input.toString() + " ");
                countTime+=1;
           }

            if (input.equalsIgnoreCase("time"))
                out.println(LocalDateTime.now());

            if (input.equalsIgnoreCase("subscribe")) {
                MultiThreadServer.listSubscribe.add(this);
                frame.textAreaCountOfSubscribers.setText(Integer.toString(MultiThreadServer.listSubscribe.size()));
           //break;
            }
            if (input.equalsIgnoreCase("wisdom")) {
                WisdomDemo wisdomDemo = new WisdomDemo("/wisdom.TXT");
                out.println(wisdomDemo.getRandomWisdom());
            break;
            }
            if (input.equalsIgnoreCase("exchange")) {
                try {
                    ExchangeRate exchangeRate = new ExchangeRate();
                    out.println(exchangeRate.getLineExchangeRate());
                } catch (UnknownHostException e) {
                    out.println("Error");
                }
                break;
            }
            if (input.equalsIgnoreCase("exit")) {
                break;
            }

        }
    }
}catch (SocketException e){
 ;//empty
}
           // out.close();
           // in.close();

//            clientDialog.getOutputStream().close();
//            clientDialog.getInputStream().close();
//           clientDialog.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread.interrupted();
    }

    public void sendMessage(String message){

        out.println(message);
        out.println(message);


    }
    public boolean closeOutInServer(){
        out.close();
        try {
            in.close();
           try {
               clientDialog.getInputStream().close();
           }catch (SocketException e){
               ;//empty
           }
           try{
            clientDialog.getOutputStream().close();
        }catch (SocketException e){
            ;//empty
        }
        if(!clientDialog.isClosed())
            clientDialog.close();


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
       return true;
    }
}