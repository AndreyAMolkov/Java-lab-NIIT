import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadServer {
    static GuiServer_Controls frame;
    static ExecutorService executeIt = Executors.newFixedThreadPool(20);
    static ArrayList<MonoThreadClientHandler>listMonoServer=new ArrayList <>(0);
    static ArrayList<MonoThreadClientHandler>listSubscribe=new ArrayList <>(0);
    static ServerSocket server;
    static Socket client;
    public static void main(String[] args) {

        int count=0;//count thread
        int port=1234;
        server = null;

        try {
            server = new ServerSocket(port);

            // стартуем цикл при условии что серверный сокет не закрыт
        if(server==null){
             for(int i=0;i<20;i++){

                 frame.textAreaStatus.setText(i+" attempt to establish connection");
             try {
                 Thread.sleep(100);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }

             if (i==19)
                 frame.textAreaStatus.setText("the server socket is closed");

                }

        }


            frame.textAreaStatus.setText("server is running");

                client = null;
                  while ( (client = server.accept())!=null) {
                      frame.textAreaStatus.setText(" Connection accepted.");

                      listMonoServer.add(new MonoThreadClientHandler(client, frame));
                      executeIt.execute(listMonoServer.get(listMonoServer.size()-1));
                      frame.textAreaCount.setText(Integer.toString(listMonoServer.size()));


                  }

            // закрытие пула нитей после завершения работы всех нитей
            executeIt.shutdown();
                  server.close();
            frame.textAreaStatus.setText("Exit");
    }catch (SocketException e) {

            try {
                server.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            e.printStackTrace();
    } catch (IOException e){
            e.printStackTrace();
        }
    }

}
