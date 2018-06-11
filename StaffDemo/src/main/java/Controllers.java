
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class Controllers  extends Application {
    private ObservableList<ConsolidatedList> observableListData=FXCollections.observableArrayList();
    private ArrayList<Employee > staff;


    @FXML
    Pane paneStart;
    @FXML
    Pane panePayment;
    @FXML
    Button buttonStart;
    @FXML
    Button buttonBack;

    @FXML
    TableView <ConsolidatedList>tableViewData;
    @FXML
    private TableColumn<ConsolidatedList,String> tbcId;
    @FXML
    private TableColumn<ConsolidatedList,String>tbcName;
    @FXML
    private TableColumn<ConsolidatedList,String>tbcBasePerHour;
    @FXML
    private TableColumn<ConsolidatedList,String>tbcProject;
    @FXML
    private TableColumn<ConsolidatedList,String>tbcTime;
    @FXML
    private TableColumn<ConsolidatedList,String>tbcPosition;

    @FXML
    TableView <ConsolidatedList>tableViewPayment;
    @FXML
    private TableColumn<ConsolidatedList,String> tbpId;
    @FXML
    private TableColumn<ConsolidatedList,String>tbpName;
    @FXML
    private TableColumn<ConsolidatedList,String>tbpBasePerHour;
    @FXML
    private TableColumn<ConsolidatedList,String>tbpProject;
    @FXML
    private TableColumn<ConsolidatedList,String>tbpTime;
    @FXML
    private TableColumn<ConsolidatedList,String>tbpPosition;
    @FXML
    private TableColumn<ConsolidatedList,String> tbpBonus;
    @FXML
    private TableColumn<ConsolidatedList,String>tbpSubordinates;
    @FXML
    private TableColumn<ConsolidatedList,String>tbpNumberSubordinates;
    @FXML
    private TableColumn<ConsolidatedList,String>tbpRateFromManagingOfTeam;
    @FXML
    private TableColumn<ConsolidatedList,String>tbpHeadingBonus;
    @FXML
    private TableColumn<ConsolidatedList,String>tbpPayment;


    @FXML
    public void initialize() {
      panePayment.setVisible(false);

       tbcId.setCellValueFactory(new PropertyValueFactory<ConsolidatedList,String>("id"));
       tbcName.setCellValueFactory(new PropertyValueFactory<ConsolidatedList,String>("name"));
       tbcTime.setCellValueFactory(new PropertyValueFactory<ConsolidatedList,String>("workTime"));
       tbcBasePerHour.setCellValueFactory(new PropertyValueFactory<ConsolidatedList,String>("base"));
       tbcPosition.setCellValueFactory(new PropertyValueFactory<ConsolidatedList,String>("position"));
       tbcProject.setCellValueFactory(new PropertyValueFactory<ConsolidatedList,String>("project"));
       tableViewData.setItems(observableListData);
       downloadFirstData();

        tbpId.setCellValueFactory(new PropertyValueFactory<ConsolidatedList,String>("id"));
        tbpName.setCellValueFactory(new PropertyValueFactory<ConsolidatedList,String>("name"));
        tbpTime.setCellValueFactory(new PropertyValueFactory<ConsolidatedList,String>("workTime"));
        tbpBasePerHour.setCellValueFactory(new PropertyValueFactory<ConsolidatedList,String>("base"));
        tbpPosition.setCellValueFactory(new PropertyValueFactory<ConsolidatedList,String>("position"));
        tbpProject.setCellValueFactory(new PropertyValueFactory<ConsolidatedList,String>("project"));
        tbpId.setCellValueFactory(new PropertyValueFactory<ConsolidatedList,String>("id"));
        tbpBonus.setCellValueFactory(new PropertyValueFactory<ConsolidatedList,String>("bonus"));
        tbpSubordinates.setCellValueFactory(new PropertyValueFactory<ConsolidatedList,String>("subordinates"));
        tbpNumberSubordinates.setCellValueFactory(new PropertyValueFactory<ConsolidatedList,String>("numberSubordinates"));
        tbpRateFromManagingOfTeam.setCellValueFactory(new PropertyValueFactory<ConsolidatedList,String>("rateFromManagingOfTeam"));
        tbpHeadingBonus.setCellValueFactory(new PropertyValueFactory<ConsolidatedList,String>("headingBonus"));
        tbpPayment.setCellValueFactory(new PropertyValueFactory<ConsolidatedList,String>("payment"));


        tableViewPayment.setItems(observableListData);

    }
    private void downloadFirstData(){
        Runnable st=()-> {
            String path = "/Staff of company.xlsx";
            Payroll payroll = null;
            try {
                payroll = new Payroll(path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.staff = payroll.getStaff();
            ArrayList <WorkProject> workProjects = payroll.getWorkProjects();

            for (WorkProject workProject : workProjects)
                workProject.calculateBonusOfProject(payroll.getCostPersonnel());
            for (WorkProject workProject : workProjects)
                workProject.calculateBonusOfProjectForSeniorManager();
        };
        Thread calculation=new Thread(st);
        calculation.setDaemon(true);

        calculation.start();

        try {
            calculation.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CollectConsolidatedList collectConsolidatedList=new CollectConsolidatedList();
        ArrayList<ConsolidatedList>listData= collectConsolidatedList.collect(staff);
        observableListData.addAll(listData);
    }
public void onClickStart() {

   paneStart.setVisible(false);
   panePayment.setVisible(true);

}
    public void onClickBack() {

        paneStart.setVisible(true);
        panePayment.setVisible(false);

    }


@Override
public void start(Stage primaryStage) throws Exception{


    Parent root = FXMLLoader.load(getClass().getResource("/StaffGUI.fxml"));
    primaryStage.setTitle("Staff demo");
    primaryStage.setScene(new Scene(root, 900, 900));
    primaryStage.show();
}


    public static void main(String[] args) {
        launch(args);
    }
}
