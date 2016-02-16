/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFXpt2;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.shape.VLineTo;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Mikko
 */
public class FXMain extends Application {

    private void checkBounds(Circle terve, Circle sairas) {

        DoubleProperty xValue = new SimpleDoubleProperty();
        xValue.bind(terve.centerXProperty());
        xValue.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {

                System.out.println(" Current center X : " + (double) arg2);
            }
        });
    }

    @Override
    public void start(Stage primaryStage) {

        Pane root = new Pane();
        Circle terve = new Circle(20, Color.LIGHTGREEN);
        Circle sairas = new Circle(20, Color.RED);

        root.getChildren().addAll(terve, sairas);

        Path path = new Path();
        path.getElements().addAll(new MoveTo(100, 300), new HLineTo(850));
        path.setFill(null);
        path.setVisible(false);
        root.getChildren().add(path);

        Path path2 = new Path();
        path2.getElements().addAll(new MoveTo(500, 100), new VLineTo(850));
        path2.setFill(null);
        path2.setVisible(false);
        root.getChildren().add(path2);

        Stage stage = new Stage();

        Scene scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.show();

        PathTransition pt = new PathTransition(Duration.millis(4000), path, terve);
        PathTransition pt2 = new PathTransition(Duration.millis(6000), path2, sairas);
        //pt.setCycleCount(Animation.INDEFINITE);
        //pt.setAutoReverse(true);
        pt.play();
        pt2.play();

        checkBounds(terve, sairas);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
