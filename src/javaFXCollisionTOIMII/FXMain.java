/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFXCollisionTOIMII;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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
 * @author Mikko s
 */
public class FXMain extends Application {

    AnimationTimer animationTimer;
    public Random rand = new Random();
    private ArrayList<Shape> circles;

    // Havaitsee törmäyksen ja värjää ensimmäisenä annetun ympyrän punaiseksi
    private void collision(Circle c1, Circle c2) {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (c1.getBoundsInParent().intersects(c2.getBoundsInParent())) {
                    animationTimer.stop();
                    c1.setFill(Color.RED);
                    System.out.println("C1 info: " + c1.getBoundsInLocal());
                    System.out.println("C2 info: " + c2.getBoundsInLocal());
                }
            }
        };
        animationTimer.start();
    }

    // Havaitsee törmäyksen ja värjää ensimmäisenä annetun ympyrän punaiseksi
    private void collisionGroup(Shape block) {
        boolean collisionDetected = false;
        for (Shape static_bloc : circles) {
            if (static_bloc != block) {
                static_bloc.setFill(Color.GREEN);

                Shape intersect = Shape.intersect(block, static_bloc);
                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    collisionDetected = true;
                }
            }
        }

        if (collisionDetected) {
            block.setFill(Color.BLUE);
            System.out.println("boing");
        } else {
            block.setFill(Color.GREEN);
        }
    }

    // Palauttaa satunnaisen kokonaisluvun
    public int random(int r) {
        return rand.nextInt(r) + 1;
    }

    // Luo path-olion jota circle seuraa, metodille tulee antaa myös käytetty ryhmä
    public Path setPath(int x, int y, int lineLength, char orientation, Group group) {
        Path path = new Path();

        // Jos annettu h, linja on vaakasuora
        if (orientation == 'h' || orientation == 'H') {
            path.getElements().addAll(new MoveTo(x, y), new HLineTo(lineLength));
        }
        // Jos annettu V, linja on pystysuora
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

        Circle terve = new Circle(20, Color.LIGHTGRAY);
        Circle sairas = new Circle(20, Color.LIGHTGRAY);
        Circle terve2 = new Circle(20, Color.LIGHTGRAY);
        Group root = new Group(terve, sairas, terve2);

        circles = new ArrayList<>();
        circles.add(terve);
        circles.add(terve2);
        circles.add(sairas);

        Stage stage = new Stage();

        Scene scene = new Scene(root, 800, 800, true);
        stage.setScene(scene);

        collision(terve, sairas);
        //collisionGroup(circles.get(circles.size() - 1));

        stage.show();

        PathTransition pt = new PathTransition(Duration.millis(6000), setPath(100, 200, 850, 'h', root), terve);
        PathTransition pt2 = new PathTransition(Duration.millis(6000), setPath(200, 100, 850, 'v', root), sairas);
        PathTransition pt3 = new PathTransition(Duration.millis(11000), setPath(500, 10, 850, 'v', root), terve2);

        pt.play();
        pt2.play();
        pt3.play();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
