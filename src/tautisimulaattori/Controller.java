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
    public void collision(ModelSubjects terveet, ModelSubjects sairaat, ModelSickness tauti) {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {

                for (ModelSubject c1 : sairaat.getMap().keySet()) {
                    for (ModelSubject c2 : terveet.getMap().keySet()) {
                        if (c1.getCircle().getBoundsInParent().intersects(c2.getCircle().getBoundsInParent())) {
                            c2.getCircle().setFill(Color.RED);
                            c2.setSick(true);
                            if (tauti.getStrength() >= c2.getHealth()) {
                                terveet.getMap().get(c2).stop();
                                c2.setDead(true);
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
    public void doTimeline(Group circles, int amount, ModelSubjects subjects, Color color, boolean isSick) {
        int sX, sY, eX, eY;

        for (int i = 0; i < amount; i++) {
            if (random(10) % 2 == 0) {
                sX = random(800);
                sY = 0;
                eX = 805;
                eY = random(805);            
            } else {
                sX = 0;
                sY = random(800);
                eX = random(805);
                eY = 805;
                
            }
            ModelSubject cModel = new ModelSubject(new Circle(5, color), random(101), isSick);
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
}
