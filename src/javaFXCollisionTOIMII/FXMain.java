/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFXCollisionTOIMII;

import java.util.Random;
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
 * @author Mikko s
 */
public class FXMain extends Application {

    AnimationTimer animationTimer;
    public Random rand = new Random();

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

    // Palauttaa satunnaisen kokonaisluvun
    public int random(int r) {
        return rand.nextInt(r) + 1;
    }

    // Luo pat-olion jota circle seuraa
    public Path setPath(int x, int y, int lineLength, char orientation, Group group) {
        Path path = new Path();

        if (orientation == 'h' || orientation == 'H') {
            path.getElements().addAll(new MoveTo(x, y), new HLineTo(lineLength));
        }

        if (orientation == 'v' || orientation == 'V') {
            path.getElements().addAll(new MoveTo(x, y), new VLineTo(lineLength));
        }

        path.setFill(null);
        path.setVisible(false);
        group.getChildren().add(path);
        return path;
    }

    @Override
    public void start(Stage primaryStage) {

        Circle terve = new Circle(20, Color.LIGHTGREEN);
        Circle sairas = new Circle(20, Color.RED);
        Group root = new Group(terve, sairas);

        Stage stage = new Stage();

        Scene scene = new Scene(root, 800, 800, true);
        stage.setScene(scene);

        collision(terve, sairas);

        stage.show();

        PathTransition pt = new PathTransition(Duration.millis(4000), setPath(100, 200, 850, 'h', root), terve);
        PathTransition pt2 = new PathTransition(Duration.millis(4000), setPath(200, 100, 850, 'v', root), sairas);
        
//        PathTransition pt = new PathTransition(Duration.millis(4000), setPath(random(800), random(800), 850, 'h', root), terve);
//        PathTransition pt2 = new PathTransition(Duration.millis(4000), setPath(random(800), random(800), 850, 'v', root), sairas);

        
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
