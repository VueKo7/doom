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
    private int puntiSalute = 100;
    private Point3D position;
    private Vector3D vector3d;
    private double rotation, rotationSpeed = 5;

    // Costruttore
    public Player(@SuppressWarnings("exports") PerspectiveCamera camera, HandleInput input) {
        this.input = input;

        position = new Point3D(0, -5, 0);
        vector3d = Vector3D.ZERO;
        setTranslateY(position.getY());
        camera.setTranslateY(position.getY());

        //impostazione dimensioni
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setDepth(DEPTH);

        //settaggio camera
        this.camera = camera;
        this.camera.setTranslateY(position.getY());
        this.camera.setNearClip(0.1);
        this.camera.setFarClip(1000.0);
        this.camera.setRotationAxis(Point3D.convertToJavaFXPoint3D(position));
        setRotationAxis(camera.getRotationAxis());
    }


    public void update() 
    {
        handleInput(); //leggo l'input dell'utente e modifico Vector3D
        //lavoro sullo spostamento e modifico Point3D
        double playerDeltaX = Math.sin(Math.toRadians(camera.getRotate())) * -Math.signum(camera.getRotate());
        double playerDeltaZ = Math.cos(Math.toRadians(camera.getRotate())) * Math.signum(camera.getRotate());
        position.x += playerDeltaX;
        position.z += playerDeltaZ;
        
        updateMovement(); //applico il movimento al cubo    
        updateCamera(); //applico il movimento alla camera
    }

    private void updateMovement() 
    {
        setTranslateX(position.getX());
        setTranslateY(position.getY());
        setTranslateZ(position.getZ());
    }

    private void updateCamera() 
    {
        camera.setTranslateX(position.getX());
        camera.setTranslateY(position.getY());
        camera.setTranslateZ(position.getZ());
        
        camera.setRotate(rotation);
        this.setRotate(rotation);
    }
    


    //leggo le richieste dell'utente dal buffer
    private void handleInput()
    {
        //fermo
        Vector3D reqVector3d = Vector3D.ZERO;    

        //movimento
        if(input.getKeyState(KeyCode.W)) {
            reqVector3d.add(Vector3D.FORWARD);
        }
        if(input.getKeyState(KeyCode.A)) {
            reqVector3d.add(Vector3D.LEFT);
        }
        if(input.getKeyState(KeyCode.S)) {
            reqVector3d.add(Vector3D.BACK);
        }
        if(input.getKeyState(KeyCode.D)) {
            reqVector3d.add(Vector3D.RIGHT);
        }

        //rotazione
        if(input.getKeyState(KeyCode.E)) {
            rotation += 1 * rotationSpeed;
        }
        else if(input.getKeyState(KeyCode.Q)) {
            rotation -= 1 * rotationSpeed;
        }

        vector3d.setAs(reqVector3d);
        // vector3d.normalize();
        System.out.println(vector3d.toString());
    }





















    public HandleInput getInput() {
        return input;
    }


    public void setInput(HandleInput input) {
        this.input = input;
    }


    public Point3D getPosition() {
        return position;
    }


    public void setPosition(Point3D position) {
        this.position = position;
    }


    public Vector3D getVector3d() {
        return vector3d;
    }


    public void setVector3d(Vector3D vector3d) {
        this.vector3d = vector3d;
    }




    // Metodi per accedere e modificare i campi
    public int getPuntiSalute() {return puntiSalute;}
    public void setPuntiSalute(int puntiSalute) {this.puntiSalute = puntiSalute;}

    // Metodo per sparare
    public void fire() {
        
    }
}
