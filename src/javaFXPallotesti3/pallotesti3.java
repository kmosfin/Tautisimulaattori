/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFXPallotesti3;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class pallotesti3 extends Application {

    final static int WIDTH = 1000;
    final static int HEIGHT = 600;
    final static int RADIUS = 50;
    Circle ball;
    AnimationTimer animationTimer;
    double osX;
    Rectangle wall;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Collision test");

        wall = RectangleBuilder.create() // spodná stena
                .width(1 * WIDTH)
                .height(1 * HEIGHT)
                .fill(Color.BLUE)
                .translateX(0.5 * WIDTH)
                .translateY(0 * WIDTH)
                .translateZ(0 * WIDTH)
                .rotationAxis(Rotate.Y_AXIS)
                .rotate(90)
                .build();

        ball = CircleBuilder.create()
                .radius(RADIUS)
                .layoutX(WIDTH / 2)
                .layoutY(HEIGHT / 2)
                .fill(Color.RED)
                .smooth(true)
                .build();

        Group root = new Group(wall, ball);
        root.setDepthTest(DepthTest.ENABLE);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT, true));
        primaryStage.getScene().setCamera(new PerspectiveCamera());
        ball.setTranslateZ(300);

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (ball.getBoundsInParent().intersects(wall.getBoundsInParent())) {
                    animationTimer.stop();
                    System.out.println("Animation stopped.");
                    System.out.println("Ball info: " + ball.getBoundsInLocal());
                    System.out.println("Wall info: " + wall.getBoundsInLocal());
                } else {
                    ball.setCenterX(osX += 5);
                }
            }
        };
        animationTimer.start();
        primaryStage.show();
    }
}
