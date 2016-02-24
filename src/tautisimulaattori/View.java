/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tautisimulaattori;

import javafx.animation.Timeline;

/**
 *
 * @author Mikko, Taneli, Aleksanteri
 */
public class View {

    public void playTimeline(Model subject) {
        for (Timeline t : subject.getMap().values()) {
            t.play();
        }
    }
}
