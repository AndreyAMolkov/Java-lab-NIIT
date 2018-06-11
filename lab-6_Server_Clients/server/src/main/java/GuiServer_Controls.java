import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

import static javafx.application.Application.launch;


public class GuiServer_Controls extends Application {

private Thread headThread;
    @FXML
    Button buttonStartSubscribers;
    @FXML
    TextArea textAreaMessageForSubscribers;
    @FXML
    TextArea textAreaTimerForSubscribers;
    @FXML
    TextArea textAreaCountOfSubscribers;
    @FXML
    TextArea textAreaCount;
    @FXML
    TextArea textAreaStatus;
    @FXML
    TextArea textAreaRequest;
    @FXML
    Button buttonStopServer;
    @FXML
    Button buttonStartServer;



    public void initialize(){
;//empty
    }

    public void onClickStartServer(){
        MultiThreadServer.frame=this;
        Runnable s=()-> {
        MultiThreadServer multiThreadServer = new MultiThreadServer();
        multiThreadServer.main(new String[0]);
    };

    Thread server=new Thread(s);
    this.headThread=server;
    server.setDaemon(true);
    server.start();

    buttonStartServer.setVisible(false);
    }
    public void onClickSubscribe(){
       if(MultiThreadServer.listSubscribe.size()>0&&Integer.parseInt(textAreaTimerForSubscribers.getText())>0) {
           buttonStartSubscribers.setVisible(false);
           Integer timerSubscribe = Integer.parseInt(textAreaTimerForSubscribers.getText());
           final String message;
           String buf=textAreaMessageForSubscribers.getText();
           if (textAreaMessageForSubscribers.getText().equals(""))
               message = "a new wisdom every " + timerSubscribe + " sec, touch the button 'a random wisdom'";
                else
               message= textAreaMessageForSubscribers.getText();
           if (timerSubscribe > 0) {
               Runnable tS = () -> {
                   try {

                       Integer remainder;
                       for (Integer i = 0; i <=timerSubscribe; i++) {
                           Thread.sleep(1000);
                           remainder = timerSubscribe - i;
                           textAreaTimerForSubscribers.setText(remainder.toString());
                       }

                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }

                   for (MonoThreadClientHandler m : MultiThreadServer.listSubscribe)
                       m.sendMessage(message);
                   buttonStartSubscribers.setVisible(true);
               };
               Thread subscribe = new Thread(tS);
               subscribe.start();




           }

       }
    }
    public void onClickStopClientPanel(){
        MultiThreadServer.executeIt.shutdown();
        for(MonoThreadClientHandler m:MultiThreadServer.listMonoServer) {
            if (m.closeOutInServer()) ;
            System.out.println("error close for MonoThreadClient -" + m.toString());


        }
        MultiThreadServer.listMonoServer.clear();
        try {
           while ( MultiThreadServer.server.isClosed())
            MultiThreadServer.server.close();

            buttonStartServer.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            System.out.println("Server is close");
        }


    }
    public static void main (String[] args) {
    try {

    launch();
    }catch (NullPointerException e){
    System.out.println("Error launch of server panel");
    }

    }

    @Override
    public void  start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GuiServer.fxml"));
        primaryStage.setTitle("the panel of server");
        primaryStage.setScene(new Scene(root, 800, 900));
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                onClickStopClientPanel();
                while ( MultiThreadServer.server.isClosed()) {
                    try {
                        MultiThreadServer.server.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("Stage is closing");
                primaryStage.close();
            }
        });

    }

    public GuiServer_Controls getGUI(){
        return this;
    }
}
