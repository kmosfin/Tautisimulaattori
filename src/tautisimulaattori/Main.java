/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tautisimulaattori;

import com.sun.java.swing.plaf.windows.resources.windows;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.Timeline;
import static javafx.application.Application.launch;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import javafx.application.Platform;

/**
 *
 * @author Mikko, Taneli, Aleksanteri
 */
public class Main extends Application {

    Controller cont = new Controller();
    View view = new View();

    ModelSubjects sairaat = new ModelSubjects();
    ModelSubjects terveet = new ModelSubjects();
    ModelSickness tauti = new ModelSickness();

    Timer timer = new Timer();

    int laskuri = 0;
    int sairastuneita = 0;
    int kuolleita = 0;
    int sAmount = 5;
    int tAmount = 100;
    int sTulos = 0;
    int kTulos = 0;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Group circles = new Group();

        tauti.setStrength(cont.random(100));
        //tauti.setStrength(200);

        cont.doTimeline(circles, sAmount, sairaat, Color.RED, true);
        cont.doTimeline(circles, tAmount, terveet, Color.LIGHTGREEN, false);

        root.getChildren().add(circles);

        Stage stage = new Stage();

        Scene scene = new Scene(root, 800, 800, true);
        stage.setScene(scene);

        stage.show();

        view.playTimeline(sairaat);
        view.playTimeline(terveet);

        cont.collision(terveet, sairaat, tauti);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("LOPPURAPORTTI:");
                System.out.println("Nollapotilaita osallistujista: " + sAmount);
                System.out.println("Alussa terveitä osallistujia: " + tAmount);
                System.out.println("Taudin voima: " + tauti.getStrength());

                for (Map.Entry<ModelSubject, Timeline> entry : sairaat.getMap().entrySet()) {
                    System.out.println("Kohde " + (laskuri + 1) + "[ Energia: " + entry.getKey().getHealth()
                            + " Sairastui: " + entry.getKey().isSick()
                            + " Kuoli: " + entry.getKey().isDead());
                    laskuri++;
                    if (entry.getKey().isSick()) {
                        sairastuneita++;
                    }
                    if (entry.getKey().isDead()) {
                        kuolleita++;
                    }
                }

                sTulos = sairastuneita - sAmount;
                if (sTulos < 0) {
                    sTulos = 0;
                }

                kTulos = kuolleita - sAmount;
                if (kTulos < 0) {
                    kTulos = 0;
                }

                System.out.println("LOPPUTULOS (Nollapotilaat mukana ( " + sAmount + "kpl )");
                System.out.println("Täysin terveinä selvisi: " + (tAmount - sTulos));
                System.out.println("Sairastunetia: " + sTulos);
                System.out.println("Sairastuneista kuoli: " + kTulos);

            }
        }, 12000);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
