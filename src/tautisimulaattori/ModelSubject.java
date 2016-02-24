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
    private boolean dead;
    private boolean sick;
    private boolean stopped;

    public ModelSubject(Circle circle, int health, boolean sick) {
        this.circle = circle;
        this.health = health;
        this.dead = false;
        this.sick = sick;
        this.stopped = false;
    }

    public Circle getCircle() {
        return circle;
    }

    public int getHealth() {
        return health;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isSick() {
        return sick;
    }

    public void setSick(boolean sick) {
        this.sick = sick;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }
    
    
 
}
