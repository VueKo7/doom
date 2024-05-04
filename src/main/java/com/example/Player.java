package com.example;

import javafx.scene.PerspectiveCamera;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Box;
import javafx.geometry.Point3D;

public class Player extends Box {

    //forma
    private static final double HEIGHT = 5;
    private static final double WIDTH = 2;
    private static final double DEPTH = 2;

    //attributi
    private HandleInput input;
    private PerspectiveCamera camera;
    private Weapon weapon;
    private Point3D position;
    private Vector3D vector3d;
    private double rotation;
    private int puntiVita = 100;

    
    // Costruttore 
    @SuppressWarnings("exports")
    public Player(PerspectiveCamera camera, HandleInput input, Weapon weapon, int x, int y, int z) {
        super(WIDTH, HEIGHT, DEPTH); //entita'
    
        this.input = input;
        this.weapon = weapon;
        this.camera = camera;

        //INIT
        position = new Point3D(x, y, z);
        vector3d = new Vector3D();
        
        //impostazione dimensioni hitbox
        setTranslateY(position.getY());
        setRotationAxis(position);

        //settaggio camera
        this.camera.setTranslateY(position.getY());
        this.camera.setFieldOfView(70);
        this.camera.setNearClip(0.1);
        this.camera.setFarClip(1000.0);
        this.camera.setRotationAxis(position);

        //settaggio arma
        this.weapon.setTranslateY(-4.65);
        this.weapon.setRotationAxis(position);
        this.weapon.setPosition(position);
    }

    
    public void update() {
        handleInput();          //leggo l'input dell'utente e modifico la posizione
        updateMovement();       //aggiornamento player
        updateCamera();         //aggiornamento camera
        updateWeapon();         //aggiornamento arma
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
        weapon.setRotate(rotation);
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
    private void handleInput() {

        //prendo la rotazione richiesta
        rotation = input.getRotation();

        //prendo l'angolo di rotazione della camera e lo trasformo in movimento
        double angleRad = Math.toRadians(camera.getRotate());

        // Fermo
        vector3d = new Vector3D();

        // Movimento
        if(input.getKeyState(KeyCode.W)) { //movimento in Z+
            vector3d = vector3d.add(-Math.sin(angleRad), 0, Math.cos(angleRad));
        }
        if(input.getKeyState(KeyCode.A)) { //movimento in X-
            vector3d = vector3d.add(-Math.sin(angleRad +90), 0, -Math.sin(angleRad));
        }
        if(input.getKeyState(KeyCode.S)) {  //movimento in Z-
            vector3d = vector3d.add(Math.sin(angleRad), 0, -Math.cos(angleRad));
        }
        if(input.getKeyState(KeyCode.D)) { //movimento in X+
            vector3d = vector3d.add(-Math.sin(angleRad -90), 0, Math.sin(angleRad));
        }


        //controllo che nella direzione richiesta non ci siano muri, solo alla richiesta di input
        if(input.getKeyState(KeyCode.W) || input.getKeyState(KeyCode.A) || input.getKeyState(KeyCode.S) || input.getKeyState(KeyCode.D)) {
            
            //iterazione per tutti gli elementi, muri e pavimenti
            Game.getWorld().getChildren().forEach(wall -> {

                //filtro per muri (id == 1)
                if(wall.getId().equals("1")) {
                    
                    //controllo la collisione con un muro nella direzione richiesta
                    boolean xCollision = collisionX((Box)wall, vector3d.getX());
                    boolean zCollision = collisionZ((Box)wall, vector3d.getZ());

                    //controllo che la collisione avvenga con l'oggetto per entrambe le coordinate
                    if(zCollision && xCollision) {      
                        //controlla se alle sue spalle ha un ostacolo
                        xCollision = collisionX((Box)wall, -vector3d.getX());
                        zCollision = collisionZ((Box)wall, -vector3d.getZ());
                        
                        //impone che tu NON abbia un'ostacolo a destra/sinistra per fermarti in X
                        if(!xCollision) vector3d.x = 0;
                        
                        //impone che tu NON abbia un'ostacolo avanti/dietro per fermarti in Z
                        if(!zCollision) vector3d.z = 0;
                    }
                } 
            });
        } //fine check collisioni
        
        //aggiorno la posizione
        position = position.add(vector3d.getX(), vector3d.getY(), vector3d.getZ()); 
    }



        //COLLISIONI
//************************************************************* */
    public boolean collisionX(@SuppressWarnings("exports") Box wall, double dX) {

        double observerX = getTranslateX();
        double observerWidth = WIDTH+1;

        double entityX = wall.getTranslateX();
        double entityWidth = wall.getWidth();

        return (observerX+observerWidth+dX >= entityX && observerX+dX <= entityX+entityWidth); 
    }

    public boolean collisionZ(@SuppressWarnings("exports") Box wall, double dY) {

        double observerZ = getTranslateZ();
        double observerHeight = DEPTH+1;

        double entityZ = wall.getTranslateZ();
        double entityHeight = wall.getDepth();

        return (observerZ+observerHeight+dY >= entityZ && observerZ+dY <= entityZ+entityHeight); 
    }

    //Metodo getter della posizione
    @SuppressWarnings("exports")
    public Point3D getPosition() {
        return position;
    }

    public void setPosition(@SuppressWarnings("exports") Point3D position) {
        this.position = position;
    }

    //Metodo getter dei punti vita
    public int getPuntiVita() {
        return puntiVita;
    }

    //Metodo setter dei puntiVita
    public void setPuntiVita (int vitaRimasta){
        this.puntiVita = vitaRimasta;
    }

//********************************************************* */


    public Weapon getWeapon() {
        return weapon;
    }

}
