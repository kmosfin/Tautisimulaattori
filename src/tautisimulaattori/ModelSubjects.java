/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tautisimulaattori;

import java.util.HashMap;
import javafx.animation.Timeline;

/**
 *
 * @author Mikko, Taneli, Aleksanteri
 */
public class ModelSubjects {

    private HashMap<ModelSubject, Timeline> subjects = new HashMap();

    public ModelSubjects() {      
    }    

    public HashMap<ModelSubject, Timeline> getMap() {
        return subjects;
    }

    public void SetMap(HashMap<ModelSubject, Timeline> map) {
        this.subjects = map;
    }    
}
