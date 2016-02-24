/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tautisimulaattori;

import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 *
 * @author Mikko, Taneli, Aleksanteri
 */
public class Controller {

    AnimationTimer animationTimer;
    public Random rand = new Random();

    // Havaitsee tˆrm‰yksen ja v‰rj‰‰ ensimm‰isen‰ annetun ympyr‰n punaiseksi
    public void collision(Model terveet, Model sairaat) {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {

                for (Circle c1 : sairaat.getMap().keySet()) {
                    for (Circle c2 : terveet.getMap().keySet()) {
                        if (c1.getBoundsInParent().intersects(c2.getBoundsInParent())) {
                            c2.setFill(Color.RED);
                            if (random(100) >= 50) {
                                terveet.getMap().get(c2).stop();
                            }
                            sairaat.getMap().put(c2, terveet.getMap().get(c2));
                            terveet.getMap().remove(c2);
                        }
                    }
                }
            }
        };
        animationTimer.start();
    }

    // Palauttaa satunnaisen kokonaisluvun
    public int random(int r) {
        return rand.nextInt(r) + 1;
    }
    
    //Pit‰‰ko palauttaa jotain ?
    public void doTimeline(Group circles, int amount, Model subject, Color color) {
        for (int i = 0; i < amount; i++) {
            Circle circle = new Circle(10, color);
            circles.getChildren().add(circle);

            Timeline timeline = new Timeline();
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, // set start position at 0
                            new KeyValue(circle.translateXProperty(), 0),
                            new KeyValue(circle.translateYProperty(), random(800))),
                    new KeyFrame(new Duration(4000 + random(6000)), // set end position at 40s
                            new KeyValue(circle.translateXProperty(), 900),
                            new KeyValue(circle.translateYProperty(), random(800))));
            subject.getMap().put(circle, timeline);
        }
    }
}
