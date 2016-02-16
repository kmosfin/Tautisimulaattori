/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxrandomrectangles;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorInput;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Mikko
 */
public class JavaFXRandomRectangles extends Application {

    public Random rand = new Random();

    public int random(int r) {
        return rand.nextInt(r) + 1;
    }

    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();
        Scene scene = new Scene(root, 900, 900, Color.WHITE);

        primaryStage.setScene(scene);

        Group tyypit = new Group();
        for (int i = 0; i < 200; i++) {
            
         //kommentti   
            Rectangle r = new Rectangle();
            r.setY(random(800));
            r.setX(random(800));
            r.setWidth(10);
            r.setHeight(10);
            r.setFill(Color.LIGHTGREEN);
            tyypit.getChildren().add(r);
        }

        root.getChildren().add(tyypit);

        Timeline timeline = new Timeline();
        for (Node r : tyypit.getChildren()) {
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(r.translateXProperty(), random(200)),
                            new KeyValue(r.translateYProperty(), random(200))),
                    new KeyFrame(new Duration(5000),
                            new KeyValue(r.translateXProperty(), random(800)),
                            new KeyValue(r.translateYProperty(), random(800))),
                    new KeyFrame(new Duration(5000),
                            new KeyValue(r.translateXProperty(), random(200)),
                            new KeyValue(r.translateYProperty(), random(200)))
            );
            r.boundsInParentProperty().addListener(new ChangeListener<Bounds>(){
                @Override
                public void changed(ObservableValue<? extends Bounds> arg0, Bounds oldValue, Bounds newValue){
                    if(r.getBoundsInLocal().intersects(newValue)){
                        r.setStyle("-fx-fill: red;");
                        //System.out.println("boing");
                    }
                }
            });
        }
        //timeline.setAutoReverse(true);
        //timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
