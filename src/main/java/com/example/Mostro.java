package com.example;

import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Mostro extends Box {

    private double speed;      //Velocità di movimento del mostro
    private int puntiVita;      //Punti vita del mostro
    private Point3D position;
    private Player player;

    public Mostro(Player player, double width, double height, double  depth, 
                    double speed, int puntiVita, int x, int y, int z) {
        super(width, height, depth);
        position = new Point3D(x, y, z);
        setSpeed(speed);
        setPuntiVita(puntiVita);
        this.player = player;
        setRotationAxis(player.getPosition());

        // Creazione di un materiale con un colore specifico
        //impostazione texture
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("/textures/finalBoss.jpg")));                  // Colore del mostro (rosso)
        setMaterial(material);                                  // Applica il materiale alla forma del mostro
    }

    public void update() {
        Point3D playerPosition = player.getPosition();
        Point3D directionToPlayer = playerPosition.subtract(position).normalize();           // Calcola la direzione dal mostro al giocatore
        Point3D movementDirection = directionToPlayer.multiply(1);                  // Si muove verso il giocatore
        position = position.add(movementDirection.multiply(speed));                       // Aggiorna la posizione del mostro
        setTranslateX(position.getX());
        setTranslateY(position.getY());
        setTranslateZ(position.getZ());
        setRotate(player.getRotate());
    }

    public void colpisci(int danno){
        int vitaRimasta = player.getPuntiVita() - danno;
        if(vitaRimasta > 0)
            player.setPuntiVita(vitaRimasta);
        else
            System.exit(0);
    }

    //Metodo setter di speed, obbliga l'inserimento di un valore compreso tra 0.1 e 10.
    public void setSpeed(double speed) {
        if(speed <= 0)
            this.speed = 0.1;
        else if (speed > 10)
            this.speed = 10;
        else
            this.speed = speed;
    }

    //Metodo setter di puntiVita, obbliga l'inserimento di un valore compreso tra 10 e 1000.
    public void setPuntiVita(int puntiVita) {
        if(puntiVita < 10)
            this.puntiVita = 10;
        else if (puntiVita > 1000)
            this.puntiVita = 1000;
        else
            this.puntiVita = puntiVita;
    }

    
   
}
