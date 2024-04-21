package com.example;

import javafx.scene.PerspectiveCamera;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Box;
import javafx.geometry.Point3D;

public class Player extends Box {

    //forma
    private static final double HEIGHT = 40;
    private static final double WIDTH = 20;
    private static final double DEPTH = 20;

    //attributi
    private HandleInput input;
    private PerspectiveCamera camera;
    private Point3D position;
    private Vector3D vector3d;
    private double speed = 5;
    private double rotation;

    // Costruttore
    public Player(@SuppressWarnings("exports") PerspectiveCamera camera, HandleInput input) {
        this.input = input;

        position = new Point3D(0, -5, 0);
        vector3d = new Vector3D();
        setTranslateY(position.getY());
        camera.setTranslateY(position.getY());

        //impostazione dimensioni
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setDepth(DEPTH);

        //settaggio camera
        this.camera = camera;
        this.camera.setFieldOfView(90);
        this.camera.setNearClip(0.1);
        this.camera.setFarClip(1000.0);
        
        this.camera.setRotationAxis(position);
        this.setRotationAxis(position);
    }

    
    public void update() 
    {
        handleInput(); //leggo l'input dell'utente e modifico Vector3D
        updateMovement(); //applico il movimento al cubo    
        updateCamera(); //applico il movimento alla camera
    }


    private void updateMovement() 
    {
        setTranslateX(position.getX());
        setTranslateY(position.getY());
        setTranslateZ(position.getZ());

        setRotate(rotation);
    }


    private void updateCamera() 
    {
        camera.setTranslateX(position.getX());
        camera.setTranslateY(position.getY());
        camera.setTranslateZ(position.getZ());
        
        camera.setRotate(rotation);
    }
    

    //leggo le richieste dell'utente dal buffer
    private void handleInput()
    {
        //prendo l'angolo di rotazione della camera e lo trasformo in movimento
        double angleRad = Math.toRadians(camera.getRotate());
        double playerDeltaX = 0; // Movimento DESTRA && SINISTRA -Math.sin(angleRad);
        double playerDeltaZ = 0; // Movimento AVANTI && INDIETRO Math.cos(angleRad);

        // Fermo
        // double dX = 0, dZ = 0;

        // Movimento
        if(input.getKeyState(KeyCode.W)) {
            playerDeltaZ = Math.cos(angleRad);
            playerDeltaX = -Math.sin(angleRad);
        }
        if(input.getKeyState(KeyCode.A)) {
            playerDeltaZ = -Math.sin(angleRad);
            playerDeltaX = -Math.sin(angleRad +90);
        }
        if(input.getKeyState(KeyCode.S)) {
            playerDeltaZ = -Math.cos(angleRad);
            playerDeltaX = Math.sin(angleRad);
        }
        if(input.getKeyState(KeyCode.D)) {
            playerDeltaZ = Math.sin(angleRad);
            playerDeltaX = -Math.sin(angleRad -90);
        }

        
        position = position.add(playerDeltaX, 0, playerDeltaZ); 

        //prendo la rotazione richiesta
        rotation = input.getRotation();
    }
// vector3d.setValues(dX, 0, dZ);
        // vector3d.normalize();
        // vector3d.multiply(speed);


}
