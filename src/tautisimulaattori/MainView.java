/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tautisimulaattori;

import java.util.Map;
import java.util.Timer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author Mikko, Taneli, Aleksanteri
 */
public class MainView extends Application {

    Controller controller = new Controller();
    CountersAndVariables cv = new CountersAndVariables();

    ModelSubjects sickSubjects = new ModelSubjects();
    ModelSubjects healthySubjects = new ModelSubjects();
    ModelSickness disease = new ModelSickness();

    Timer timer = new Timer();

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 200, 150);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(10);

        Text terveetT = new Text("Terveiden m‰‰r‰: ");
        grid.add(terveetT, 0, 0);
        TextField text1 = controller.doTbInputInt();
        text1.setPrefColumnCount(10);
        grid.add(text1, 1, 0);

        Text sairaatT = new Text("Sairaiden m‰‰r‰: ");
        grid.add(sairaatT, 0, 1);
        TextField text2 = controller.doTbInputInt();
        text2.setPrefColumnCount(10);
        grid.add(text2, 1, 1);

        Text voima = new Text("Taudin voima: ");
        grid.add(voima, 0, 2);
        TextField text3 = controller.doTbInputInt();
        text3.setPrefColumnCount(10);
        grid.add(text3, 1, 2);

        Button aja = new Button("Aja");
        GridPane.setHalignment(aja, HPos.RIGHT);
        grid.add(aja, 1, 3);

        aja.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cv.setHealthySubjects(Integer.parseInt(text1.getText()));
                cv.setZeroPatients(Integer.parseInt(text2.getText()));
                disease.setStrength(Integer.parseInt(text3.getText()));

                try {

                    Group root2 = new Group();
                    Group circles = new Group();

                    Button report = new Button();
                    report.setLayoutX(30);
                    report.setLayoutY(10);
                    report.setText("N‰yt‰ raportti");
                    report.setOnAction(e -> showReport());

                    cv.setTotalSubjects(cv.getHealthySubjects() + cv.getZeroPatients());

                    controller.doTimeline(circles, cv.getZeroPatients(), sickSubjects, Color.RED, true, cv.getCircleSize());
                    controller.doTimeline(circles, cv.getHealthySubjects(), healthySubjects, Color.LIGHTGREEN, false, cv.getCircleSize());

                    root2.getChildren().addAll(circles, report);

                    Stage stage = new Stage();

                    Scene sceneSimu = new Scene(root2, 800, 800, true);
                    stage.setScene(sceneSimu);

                    stage.show();

                    controller.playTimeline(sickSubjects);
                    controller.playTimeline(healthySubjects);

                    controller.collision(healthySubjects, sickSubjects, disease.getStrength());

                } finally {

                }
            }
        });

        root.setCenter(grid);
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showReport() {
        String txtHealthySubjects = "Ei tartuntaa ";
        String txtSickSubjects = "Tartunta, elossa ";
        String txtDeadSubjects = "Tartunta, kuolleet ";
        
        Stage pieStage = new Stage();
        Scene pieScene = new Scene(new Group());
        pieScene.getStylesheets().add("src/style.css");

        Button lopeta = new Button("Lopeta");
        lopeta.setLayoutX(425);
        lopeta.setLayoutY(425);
        lopeta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });

        for (Map.Entry<ModelSubject, Timeline> entry : sickSubjects.getMap().entrySet()) {
            cv.addCounterContact();
            if (entry.getKey().isSick()) {
                cv.addCounterSick();
            }

            if (entry.getKey().isDead()) {
                cv.addCounterDead();
            }
        }

        cv.setTotalHealthy(cv.getHealthySubjects() - cv.getCounterContact());
        if (cv.getTotalHealthy() < 0) {
            cv.setTotalHealthy(0);
        }

        txtHealthySubjects += cv.getTotalHealthy() + "kpl ";
        txtSickSubjects += cv.getCounterSick() + "kpl ";
        txtDeadSubjects += cv.getCounterDead() + "kpl ";

        pieStage.setTitle("LOPPURAPORTTI");
        pieStage.setWidth(500);
        pieStage.setHeight(500);

        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data(txtHealthySubjects, cv.getTotalHealthy()),
                        new PieChart.Data(txtSickSubjects, cv.getCounterSick()),
                        new PieChart.Data(txtDeadSubjects, cv.getCounterDead()));
        final PieChart chart = new PieChart(pieChartData);

        chart.setTitle("LOPPUTULOS \n"
                + "Testikohteita: " + cv.getHealthySubjects() + "kpl\n"
                + "Nollapotilaita: " + cv.getZeroPatients() + "kpl\n"
                + "Taudin voimakkuus:" + disease.getStrength());

        ((Group) pieScene.getRoot()).getChildren().addAll(chart, lopeta);
        pieStage.setScene(pieScene);
        pieStage.show();

        /* Testitulostuksia */
//        System.out.println("counterContact:" + cv.getCounterContact());
//        System.out.println("counterSick:" + cv.getCounterSick());
//        System.out.println("counterDead:" + cv.getCounterDead());
//        System.out.println("zeroPatients:" + cv.getZeroPatients());
//        System.out.println("healthySubjects:" + cv.getHealthySubjects());
//        System.out.println("totalHealthy:" + cv.getTotalHealthy());
//        System.out.println("totalSubjects:" + cv.getTotalSubjects());
//        System.out.println("circleSize:" + cv.getCircleSize());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
