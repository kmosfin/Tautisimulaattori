/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tautisimulaattori;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.TextField;
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

    // Havaitsee t�rm�yksen ja v�rj�� ensimm�isen� annetun ympyr�n punaiseksi
    public void collision(ModelSubjects healthySubjects, ModelSubjects sickSubjects, int diseaseStrength) {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {

                for (ModelSubject c1 : sickSubjects.getMap().keySet()) {
                    for (ModelSubject c2 : healthySubjects.getMap().keySet()) {
                        if (c1.getCircle().getBoundsInParent().intersects(c2.getCircle().getBoundsInParent())) {
                            if (diseaseStrength >= c2.getHealth()) {
                                healthySubjects.getMap().get(c2).stop();
                                c2.getCircle().setFill(Color.BLACK);
                                c2.setDead(true);
                            } else {
                                c2.getCircle().setFill(Color.RED);
                                c2.setSick(true);
                            }
                            sickSubjects.getMap().put(c2, healthySubjects.getMap().get(c2));
                            healthySubjects.getMap().remove(c2);
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

    //Pit��ko palauttaa jotain ?
    public void doTimeline(Group circles, int amount, ModelSubjects subjects, Color color, boolean isSick, int circleSize) {
        int sX, sY, eX, eY;
        int max = 700 + circleSize;

        for (int i = 0; i < amount; i++) {
            if (random(10) % 2 == 0) {
                sX = random(800);
                sY = 0;
                eX = max;
                eY = random(max);
            } else {
                sX = 0;
                sY = random(800);
                eX = random(max);
                eY = max;
            }

            ModelSubject cModel = new ModelSubject(new Circle(circleSize, color), random(101), isSick);
            circles.getChildren().add(cModel.getCircle());

            Timeline timeline = new Timeline();
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, // set start position at 0
                            new KeyValue(cModel.getCircle().translateXProperty(), sX),
                            new KeyValue(cModel.getCircle().translateYProperty(), sY)),
                    new KeyFrame(new Duration(2000 + random(8000)), // set end position at 40s
                            new KeyValue(cModel.getCircle().translateXProperty(), eX),
                            new KeyValue(cModel.getCircle().translateYProperty(), eY)));
            subjects.getMap().put(cModel, timeline);
        }
    }

    public void playTimeline(ModelSubjects subjects) {
        for (Timeline t : subjects.getMap().values()) {
            t.play();
//            t.setOnFinished(e -> c.addCounterTimelinesDone());
        }
    }

    public TextField doTbInputInt() {
        TextField textField = new TextField() {
            @Override
            public void replaceText(int start, int end, String text) {
                if (text.matches("[0-9]*")) {
                    super.replaceText(start, end, text);
                }
            }

            @Override
            public void replaceSelection(String text) {
                if (text.matches("[0-9]*")) {
                    super.replaceSelection(text);
                }
            }
        };
        return textField;
    }

}
