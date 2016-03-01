/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tautisimulaattori;

/**
 *
 * @author Mikko
 */
public class CountersAndVariables {

    private int counterContact;
    private int counterSick;
    private int counterDead;
    private int zeroPatients;
    private int healthySubjects;
    private int totalHealthy;
    private int totalSubjects;
    private int circleSize;

    public CountersAndVariables() {
        this.counterContact = 0;
        this.counterSick = 0;
        this.counterDead = 0;
        this.totalHealthy = 0;
        this.zeroPatients = 0;
        this.healthySubjects = 0;
        this.totalSubjects = 0;
        this.circleSize = 0;
    }

    public int getTotalHealthy() {
        return totalHealthy;
    }

    public void setTotalHealthy(int totalHealthy) {
        this.totalHealthy = totalHealthy;
    }

    public int getCircleSize() {
        return circleSize;
    }

    public void setCircleSize(int subjects) {
        if (subjects < 5) {
            this.circleSize = 50;
        } else if (subjects > 5 && subjects < 10) {
            this.circleSize = 30;
        } else if (subjects > 10 && subjects < 25) {
            this.circleSize = 20;
        } else if (subjects > 25 && subjects < 50) {
            this.circleSize = 15;
        } else if (subjects > 50 && subjects < 100) {
            this.circleSize = 10;
        } else if (subjects > 100 && subjects < 300) {
            this.circleSize = 5;
        } else if (subjects > 300) {
            this.circleSize = 3;
        }
    }

    public int getCounterContact() {
        return counterContact;
    }

    public void setCounterContact(int counterContact) {
        this.counterContact = counterContact;
    }

    public void addCounterContact() {
        this.counterContact++;
    }

    public int getCounterSick() {
        return counterSick;
    }

    public void setCounterSick(int counterSick) {
        this.counterSick = counterSick;
    }

    public void addCounterSick() {
        this.counterSick++;
    }

    public int getCounterDead() {
        return counterDead;
    }

    public void setCounterDead(int counterDead) {
        this.counterDead = counterDead;
    }

    public void addCounterDead() {
        this.counterDead++;
    }

    public int getZeroPatients() {
        return zeroPatients;
    }

    public void setZeroPatients(int zeroPatients) {
        this.zeroPatients = zeroPatients;
    }

    public int getHealthySubjects() {
        return healthySubjects;
    }

    public void setHealthySubjects(int healthySubjects) {
        this.healthySubjects = healthySubjects;
    }

    public int getTotalSubjects() {
        return totalSubjects;
    }

    public void setTotalSubjects(int totalSubjects) {
        this.totalSubjects = totalSubjects;
        setCircleSize(this.totalSubjects);
    }

}
