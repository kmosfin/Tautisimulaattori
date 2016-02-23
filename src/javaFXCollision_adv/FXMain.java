/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFXCollision_adv;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author Mikko s
 */
public class FXMain extends Application {

    AnimationTimer animationTimer;
    public Random rand = new Random();
    private ArrayList<Shape> circlesAl;

    final ObservableList<Shape> shapes = FXCollections.observableArrayList();
    final ObservableList<ShapePair> intersections = FXCollections.observableArrayList();

// a helper enumeration of the various types of bounds we can work with.
    enum BoundsType {
        LAYOUT_BOUNDS, BOUNDS_IN_LOCAL, BOUNDS_IN_PARENT
    }

    ObjectProperty<BoundsType> selectedBoundsType = new SimpleObjectProperty<BoundsType>(BoundsType.LAYOUT_BOUNDS);


    Line line = new Line(25, 300, 375, 200); 
    line.setId("Line");
    line.setStrokeLineCap(StrokeLineCap.ROUND);
    line.setStroke(Color.MIDNIGHTBLUE);
    line.setStrokeWidth(5);
    
    final Anchor anchor1 = new Anchor("Anchor 1", line.startXProperty(), line.startYProperty());
    final Anchor anchor2 = new Anchor("Anchor 2", line.endXProperty(), line.endYProperty());

    // Havaitsee törmäyksen ja värjää ensimmäisenä annetun ympyrän punaiseksi
    private void collision(Circle c1, Circle c2) {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (c1.getBoundsInParent().intersects(c2.getBoundsInParent())) {
                    animationTimer.stop();
                    c1.setFill(Color.RED);
                    System.out.println("C1 info: " + c1.getBoundsInLocal());
                    System.out.println("C2 info: " + c2.getBoundsInLocal());
                }
            }
        };
        animationTimer.start();
    }

    // Havaitsee törmäyksen ja värjää ensimmäisenä annetun ympyrän punaiseksi
    private void collisionGroup(Shape block) {
        boolean collisionDetected = false;
        for (Shape static_bloc : circlesAl) {
            if (static_bloc != block) {
                static_bloc.setFill(Color.GREEN);

                Shape intersect = Shape.intersect(block, static_bloc);
                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    collisionDetected = true;
                }
            }
        }

        if (collisionDetected) {
            block.setFill(Color.BLUE);
            System.out.println("boing");
        } else {
            block.setFill(Color.GREEN);
        }
    }

    // Palauttaa satunnaisen kokonaisluvun
    public int random(int r) {
        return rand.nextInt(r) + 1;
    }

    // Luo path-olion jota circle seuraa, metodille tulee antaa myös käytetty ryhmä
    public Path setPath(int x, int y, int lineLength, char orientation, Group group) {
        Path path = new Path();

        // Jos annettu h, linja on vaakasuora
        if (orientation == 'h' || orientation == 'H') {
            path.getElements().addAll(new MoveTo(x, y), new HLineTo(lineLength));
        }
        // Jos annettu V, linja on pystysuora
        if (orientation == 'v' || orientation == 'V') {
            path.getElements().addAll(new MoveTo(x, y), new VLineTo(lineLength));
        }

        path.setFill(null);
        path.setVisible(false);
        group.getChildren().add(path);
        return path;
    }

    private void testIntersections() {
        intersections.clear();

        // for each shape test it's intersection with all other shapes.
        for (Shape src : shapes) {
            for (Shape dest : shapes) {
                ShapePair pair = new ShapePair(src, dest);
                if ((!(pair.a instanceof Anchor) && !(pair.b instanceof Anchor))
                        && !intersections.contains(pair)
                        && pair.intersects(selectedBoundsType.get())) {
                    intersections.add(pair);
                }
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {

        Circle terve = new Circle(20, Color.LIGHTGRAY);
        Circle sairas = new Circle(20, Color.LIGHTGRAY);
        Circle terve2 = new Circle(20, Color.LIGHTGRAY);
        Group root = new Group(terve, sairas, terve2);

        circlesAl = new ArrayList<>();
        circlesAl.add(terve);
        circlesAl.add(terve2);
        circlesAl.add(sairas);

        Circle[] circles = {terve, terve2, sairas};
        for (Circle circle : circles) {
            circle.centerXProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                    testIntersections();
                }
            });
            circle.centerYProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                    testIntersections();
                }
            });
        }

        Stage stage = new Stage();

        Scene scene = new Scene(root, 800, 800, true);
        stage.setScene(scene);

        //collision(terve, sairas);
        //collisionGroup(circlesAl.get(circlesAl.size() - 1));
        stage.show();

        PathTransition pt = new PathTransition(Duration.millis(6000), setPath(100, 200, 850, 'h', root), terve);
        PathTransition pt2 = new PathTransition(Duration.millis(6000), setPath(200, 100, 850, 'v', root), sairas);
        PathTransition pt3 = new PathTransition(Duration.millis(11000), setPath(500, 10, 850, 'v', root), terve2);

        pt.play();
        pt2.play();
        pt3.play();

    }

    class ShapePair {

        private Shape a, b;

        public ShapePair(Shape src, Shape dest) {
            this.a = src;
            this.b = dest;
        }
    }

    // an anchor displayed around a point.
    class Anchor extends Circle {

        Anchor(String id, DoubleProperty x, DoubleProperty y) {
            super(x.get(), y.get(), 10);
            setId(id);
            setFill(Color.GOLD.deriveColor(1, 1, 1, 0.5));
            setStroke(Color.GOLD);
            setStrokeWidth(2);
            setStrokeType(StrokeType.OUTSIDE);

            x.bind(centerXProperty());
            y.bind(centerYProperty());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
