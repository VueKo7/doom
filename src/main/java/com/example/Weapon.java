package com.example;

import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Weapon extends Box {
    
    //forma
    private static final double HEIGHT = 0.5;
    private static final double WIDTH = 0.25;
    private static final double DEPTH = 1.1;

    //risorsa
    private int bullets;
    private Point3D position;


    public Weapon(int bullets) {
        super(WIDTH, HEIGHT, DEPTH); //forma arma
        this.bullets = bullets; //proiettili iniziali

        //init for shotgun
        PhongMaterial pm = new PhongMaterial();
        pm.setDiffuseMap(new Image(
            getClass().getResourceAsStream("/textures/shotgun.png")));
        setMaterial(pm);

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

    public void setPosition(@SuppressWarnings("exports") Point3D position) {
        this.position = position;
    }
    @SuppressWarnings("exports")
    public Point3D getPosition() {
        return position;
    }
}
