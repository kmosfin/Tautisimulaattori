/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tautisimulaattori;

import javafx.scene.shape.Circle;

/**
 *
 * @author Mikko, Taneli, Aleksanteri
 */
public class ModelSubject {
    private Circle circle;
    private int health;

    public ModelSubject(Circle circle, int health) {
        this.circle = circle;
        this.health = health;
    }

    public Circle getCircle() {
        return circle;
    }

    public int getHealth() {
        return health;
    }
 
}
