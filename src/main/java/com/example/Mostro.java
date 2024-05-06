package com.example;

import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import java.util.concurrent.atomic.AtomicReference;

public class Mostro extends Box {

    private double speed;      //Velocità di movimento del mostro
    private int puntiVita;      //Punti vita del mostro
    private Point3D position;
    private Player player;

    public Mostro(Player player, double width, double height, double depth,
                  double speed, int puntiVita, int x, int y, int z) {
        super(width, height, depth);
        position = new Point3D(x, y, z);
        setSpeed(speed);
        setPuntiVita(puntiVita);
        this.player = player;

        // Creazione di un materiale con un colore specifico
        //impostazione texture
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(
            getClass().getResourceAsStream("/textures/finalBoss.jpg")));                  // Colore del mostro (rosso)
        setMaterial(material);                                  // Applica il materiale alla forma del mostro
    }

    public void update() {
        Point3D playerPosition = player.getPosition();
        Point3D directionToPlayer = playerPosition.subtract(position).normalize();           // Calcola la direzione dal mostro al giocatore
        AtomicReference<Point3D> movementDirection = new AtomicReference<>(directionToPlayer.multiply(1));                    // Si muove verso il giocatore
                            // Aggiorna la posizione del mostro

        //CONTROLLO COLLISIONI

        //iterazione per tutti gli elementi, muri e pavimenti
        Game.getWorld().getChildren().forEach(wall -> {

            //filtro per muri (id == 1)
            if(wall.getId().equals("1")) {

                //controllo la collisione con un muro nella direzione richiesta
                boolean xCollision = collisionX((Box)wall, movementDirection.get().getX());
                boolean zCollision = collisionZ((Box)wall, movementDirection.get().getZ());

                //controllo che la collisione avvenga con l'oggetto per entrambe le coordinate
                if(zCollision && xCollision) {
                    //controlla se alle sue spalle ha un ostacolo
                    xCollision = collisionX((Box)wall, -movementDirection.get().getX());
                    zCollision = collisionZ((Box)wall, -movementDirection.get().getZ());

                    //impone che tu NON abbia un'ostacolo a destra/sinistra per fermarti in X
                    if(!xCollision){
                        movementDirection.set(new Point3D(
                            0,movementDirection.get().getY(), movementDirection.get().getZ()));
                    }

                    //impone che tu NON abbia un'ostacolo avanti/dietro per fermarti in Z
                    if(!zCollision){
                        movementDirection.set(new Point3D(
                            movementDirection.get().getX(), movementDirection.get().getY(),0));
                    }
                }
            }
        });

        position = position.add(movementDirection.get().multiply(speed));
        setTranslateX(position.getX());
        setTranslateY(position.getY());
        setTranslateZ(position.getZ());
    }

    public void colpisci(int danno) {
        int vitaRimasta = player.getPuntiVita() - danno;
        if (vitaRimasta > 0)
            player.setPuntiVita(vitaRimasta);
        else
            System.exit(0);
    }

    public void subisci(int danno) {
        puntiVita -= danno;
    }

    //Metodo setter di speed, obbliga l'inserimento di un valore compreso tra 0.1 e 10.
    public void setSpeed(double speed) {
        if (speed <= 0)
            this.speed = 0.1;
        else if (speed > 10)
            this.speed = 10;
        else
            this.speed = speed;
    }

    //Metodo setter di puntiVita, obbliga l'inserimento di un valore compreso tra 10 e 1000.
    public void setPuntiVita(int puntiVita) {
        if (puntiVita < 10)
            this.puntiVita = 10;
        else if (puntiVita > 1000)
            this.puntiVita = 1000;
        else
            this.puntiVita = puntiVita;
    }

    //COLLISIONI
//************************************************************* */
    public boolean collisionX(@SuppressWarnings("exports") Box wall, double dX) {

        double observerX = getTranslateX();
        double observerWidth = 3;

        double entityX = wall.getTranslateX();
        double entityWidth = 5;

        return (observerX + observerWidth + dX >= entityX && observerX + dX <= entityX + entityWidth);
    }

    public boolean collisionZ(@SuppressWarnings("exports") Box wall, double dY) {

        double observerZ = getTranslateZ();
        double observerHeight = 3;

        double entityZ = wall.getTranslateZ();
        double entityHeight = 5;

        return (observerZ + observerHeight + dY >= entityZ && observerZ + dY <= entityZ + entityHeight);
    }

    @SuppressWarnings("exports")
    public Point3D getPosition() {
        return position;
    }

    public void setPosition(@SuppressWarnings("exports") Point3D position) {
        this.position = position;
    }

    public int getPuntiVita() {
        return puntiVita;
    }
}
