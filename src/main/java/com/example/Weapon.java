package com.example;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Weapon extends Box {
    
    //forma
    private static final double HEIGHT = 0.5;
    private static final double WIDTH = 0.25;
    private static final double DEPTH = 1.1;

    //risorsa
    private int bullets;


    public Weapon(int bullets) {
        super(WIDTH, HEIGHT, DEPTH); //forma arma
        this.bullets = bullets; //proiettili iniziali

        //init for shotgun
        setMaterial(new PhongMaterial(Color.RED));

        //movimento
        setOnRotate(event -> {
            setTranslateX(getTranslateX());
            setTranslateZ(getTranslateZ());
        });
    }


    public int getBullets() {
        return bullets;
    }

    public void shoot() {
        this.bullets--;
    }
}
