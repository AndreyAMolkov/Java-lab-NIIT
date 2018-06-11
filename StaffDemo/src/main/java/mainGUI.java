import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class mainGUI extends Application  {


        @Override
        public void start(Stage primaryStage) throws Exception{

            Parent root = FXMLLoader.load(getClass().getResource("StaffGUI.fxml"));
            primaryStage.setTitle("Staff demo");
            primaryStage.setScene(new Scene(root, 900, 900));
            primaryStage.show();
        }


        public static void main(String[] args) {
            launch(args);
        }

}
