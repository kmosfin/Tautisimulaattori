/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFXCollisionTOIMII;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Mikko
 */
public class FXMain extends Application {

    AnimationTimer animationTimer;

    // Havaitsee törmäyksen ja värjää ensimmäisenä annetun ympyrän punaiseksi
    private void collision(Circle c1, Circle c2) {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (c1.getBoundsInParent().intersects(c2.getBoundsInParent())) {
                    animationTimer.stop();
                    c1.setFill(Color.RED);
                }
            }
        };
        animationTimer.start();
    }

    @Override
    public void start(Stage primaryStage) {

        Circle terve = new Circle(20, Color.LIGHTGREEN);
        Circle sairas = new Circle(20, Color.RED);
        Group root = new Group(terve, sairas);

        root.setDepthTest(DepthTest.ENABLE);

        Path path = new Path();
        path.getElements().addAll(new MoveTo(100, 200), new HLineTo(850));
        path.setFill(null);
        path.setVisible(false);
        root.getChildren().add(path);

        Path path2 = new Path();
        path2.getElements().addAll(new MoveTo(200, 100), new VLineTo(850));
        path2.setFill(null);
        path2.setVisible(false);
        root.getChildren().add(path2);

        Stage stage = new Stage();

        Scene scene = new Scene(root, 800, 800, true);
        stage.setScene(scene);

        collision(terve, sairas);
        
        stage.show();

        PathTransition pt = new PathTransition(Duration.millis(4000), path, terve);
        PathTransition pt2 = new PathTransition(Duration.millis(4000), path2, sairas);
        //pt.setCycleCount(Animation.INDEFINITE);
        //pt.setAutoReverse(true);
        pt.play();
        pt2.play();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
