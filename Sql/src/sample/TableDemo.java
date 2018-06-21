package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class TableDemo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        TableView<Record> table= new TableView <>();
        TableColumn<Record,Integer>col1=new TableColumn <>("id");
        TableColumn<Record,Integer>col2=new TableColumn <>("Температура");
        col1.setPrefWidth(45);
        col2.setPrefWidth(100);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(col1,col2);
        Controller controller=new Controller(table,col1,col2);

        final NumberAxis xAxis=new NumberAxis();
        final NumberAxis yAxis=new NumberAxis();
        xAxis.setLabel("номер измерения");
        yAxis.setLabel("T,C");
        LineChart<Number,Number>lineChart=new LineChart <>(xAxis,yAxis);
        XYChart.Series series=new XYChart.Series();
        ObservableList<Record> data=controller.getData();
        for(Record rec:data){
            series.getData().add(new XYChart.Data(rec.getId(),rec.getTemperature()));
            lineChart.getData().add(series);
            lineChart.setCreateSymbols(false);

            BorderPane root=new BorderPane();
            HBox hBox=new HBox();

            hBox.getChildren().add(table);
            hBox.getChildren().add(lineChart);
            HBox.setHgrow(lineChart,Priority.ALWAYS);
            root.setCenter(hBox);

            primaryStage.setScene(new Scene(root));
            primaryStage.setWidth(1200);
            primaryStage.setHeight(600);
            primaryStage.setResizable(false);
            primaryStage.show();
        }

    }


}
