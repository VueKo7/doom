package com.example;

import javafx.scene.PerspectiveCamera;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Box;

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
        this.camera.setRotationAxis(Point3D.convertToJavaFXPoint3D(position));
        this.setRotationAxis(Point3D.convertToJavaFXPoint3D(position));
    }

    
    public void update() 
    {
        handleInput(); //leggo l'input dell'utente e modifico Vector3D
        
        //lavoro sullo spostamento e modifico Point3D
        
        // if(!Double.isNaN(vector3d.getX()) && !Double.isNaN(vector3d.getY()) && !Double.isNaN(vector3d.getZ())) 
        // {    
        //     double angleRad = Math.toRadians(camera.getRotate());
        //     double playerDeltaX = Math.sin(angleRad) * -1;
        //     double playerDeltaZ = Math.cos(angleRad);
        //     position.x += playerDeltaX + vector3d.x;
        //     position.z += playerDeltaZ + vector3d.z;
        // }
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
        double angleRad = Math.toRadians(camera.getRotate());
        double playerDeltaX = Math.sin(angleRad); // Movimento DESTRA && SINISTRA
        double playerDeltaZ = Math.cos(angleRad); // Movimento AVANTI && INDIETRO

        // Fermo
        double dX = playerDeltaX, dZ = playerDeltaZ;

        // Movimento
        if(input.getKeyState(KeyCode.W)) {
            dZ = playerDeltaZ;
        }
        if(input.getKeyState(KeyCode.A)) {
            dX = -playerDeltaX;
        }
        if(input.getKeyState(KeyCode.S)) {
            dZ = -playerDeltaZ;
        }
        if(input.getKeyState(KeyCode.D)) {
            dX = playerDeltaX;
        }

        position.x += dX;
        position.z += dZ;

        //prendo la rotazione richiesta
        rotation = input.getRotation();

        vector3d.setValues(dX, 0, dZ);
        vector3d.normalize();
        vector3d.multiply(speed);
        
    }



}
