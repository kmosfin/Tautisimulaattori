/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tautisimulaattori;

import static javafx.application.Application.launch;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Mikko, Taneli, Aleksanteri
 */
public class Main extends Application {

    Controller cont = new Controller();
    View view = new View();

    Model sairaat = new Model();
    Model terveet = new Model();

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Group circles = new Group();

        cont.doTimeline(circles, 1, sairaat, Color.RED);
        cont.doTimeline(circles, 55, terveet, Color.LIGHTGREEN);

        root.getChildren().add(circles);

        Stage stage = new Stage();

        Scene scene = new Scene(root, 800, 800, true);
        stage.setScene(scene);

        stage.show();
        
        view.playTimeline(sairaat);
        view.playTimeline(terveet);

        cont.collision(terveet, sairaat);

        //pt.setCycleCount(Animation.INDEFINITE);
        //pt.setAutoReverse(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
