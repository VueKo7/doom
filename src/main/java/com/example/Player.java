package com.example;

import javafx.scene.PerspectiveCamera;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.geometry.Point3D;

public class Player extends Box {

    //forma
    private static final double HEIGHT = 40;
    private static final double WIDTH = 20;
    private static final double DEPTH = 20;

    //attributi
    private HandleInput input;
    private PerspectiveCamera camera;
    private Weapon weapon;
    private Point3D position;
    private Vector3D vector3d;
    private double rotation;

    // Costruttore 
    @SuppressWarnings("exports")
    public Player(PerspectiveCamera camera, HandleInput input, Weapon weapon) {
        super(WIDTH, HEIGHT, DEPTH); //entita'
    
        this.input = input;
        this.weapon = weapon;
        this.camera = camera;

        //INIT
        position = new Point3D(0, -5, 0);
        vector3d = new Vector3D();
        
        //impostazione dimensioni hitbox
        setTranslateY(position.getY());
        setRotationAxis(position);

        //settaggio camera
        this.camera.setTranslateY(position.getY());
        this.camera.setFieldOfView(90);
        this.camera.setNearClip(0.1);
        this.camera.setFarClip(1000.0);
        this.camera.setRotationAxis(position);

        //settaggio arma
        this.weapon.setTranslateY(-4.599);
        weapon.setRotationAxis(position);
    }

    
    public void update() {
        handleInput(); //leggo l'input dell'utente e modifico Vector3D
        updateMovement(); //aggiornamento player    
        updateCamera(); //aggiornamento camera
        updateWeapon(); //aggiornamento arma
    }

    private void updateMovement() {
        //sposto la hitbox
        setTranslateX(position.getX());
        setTranslateY(position.getY());
        setTranslateZ(position.getZ());
        //imposto la rotazione
        this.setRotate(rotation);
    }

    private void updateWeapon() {
        //sposto la hitbox
        weapon.setTranslateX(position.getX());
        weapon.setTranslateZ(position.getZ());
        //imposto la rotazione
        weapon.setRotate(rotation + rotation/6);
    }

    private void updateCamera() {
        //sposto la camera
        camera.setTranslateX(position.getX());
        camera.setTranslateY(position.getY());
        camera.setTranslateZ(position.getZ());
        //imposto la rotazione
        camera.setRotate(rotation);
    }
    

    //leggo le richieste dell'utente dal buffer
    private void handleInput()
    {
        //prendo la rotazione richiesta
        rotation = input.getRotation();

        //prendo l'angolo di rotazione della camera e lo trasformo in movimento
        double angleRad = Math.toRadians(camera.getRotate());

        // Fermo
        vector3d = new Vector3D();

        // Movimento
        if(input.getKeyState(KeyCode.W)) {
            vector3d = vector3d.add(-Math.sin(angleRad), 0, Math.cos(angleRad));
        }
        if(input.getKeyState(KeyCode.A)) {
            vector3d = vector3d.add(-Math.sin(angleRad +90), 0, -Math.sin(angleRad));
        }
        if(input.getKeyState(KeyCode.S)) {
            vector3d = vector3d.add(Math.sin(angleRad), 0, -Math.cos(angleRad));
        }
        if(input.getKeyState(KeyCode.D)) {
            vector3d = vector3d.add(-Math.sin(angleRad -90), 0, Math.sin(angleRad));
        }

        //aggiorno la posizione
        position = position.add(vector3d.getX(), vector3d.getY(), vector3d.getZ()); 
    }


}
