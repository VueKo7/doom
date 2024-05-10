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

    public Mostro(Player player, double width, double height, double depth,
                  double speed, int puntiVita, int x, int y, int z) {
        super(width, height, depth);
        position = new Point3D(x, y, z);
        setSpeed(speed);
        setPuntiVita(puntiVita);
        this.player = player;

        //impostazione texture
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(
            getClass().getResourceAsStream("/textures/finalBoss.jpg")));
        setMaterial(material); // Applica il materiale alla forma del mostro
    }

    
    public void update() {
        Point3D playerPosition = player.getPosition(); //ottengo la posizione del player
        Point3D directionToPlayer = playerPosition.subtract(position).normalize().multiply(speed); // Calcola la direzione dal mostro al giocatore
        //ottengo il vettore di spostamento
        Vector3D vector3d = new Vector3D(directionToPlayer.getX(), directionToPlayer.getY(), directionToPlayer.getZ());
        
        //CONTROLLO COLLISIONI
        Game.getWorld().getChildren().forEach(wall -> {
            //filtro per muri (id == 1)
            if(wall.getId().equals("1")) {
                if(position.distance(wall.getTranslateX(), wall.getTranslateY(), wall.getTranslateZ()) < 20) {
                    //controllo la collisione con un muro nella direzione richiesta
                    boolean xCollision = collisionX((Box)wall, directionToPlayer.getX());
                    boolean zCollision = collisionZ((Box)wall, directionToPlayer.getZ());

                    //controllo che la collisione avvenga con l'oggetto per entrambe le coordinate
                    if(zCollision && xCollision) {
                        //controlla se alle sue spalle ha un ostacolo
                        xCollision = collisionX((Box)wall, -directionToPlayer.getX());
                        zCollision = collisionZ((Box)wall, -directionToPlayer.getZ());

                        //impone che tu NON abbia un'ostacolo intorno per fermarti
                        if(!xCollision || !zCollision) {
                            vector3d.setValues(0, 0, 0);
                        }
                    }

                }
            }

        });
        
        //applico il vettore alla posizione
        position = position.add(vector3d.getX(), vector3d.getY(), vector3d.getZ());
        setTranslateX(position.getX());
        setTranslateY(position.getY());
        setTranslateZ(position.getZ());

        //possibilità di danneggiare il player
        if(position.distance(playerPosition) < 1) {
            player.subisci(1);
        }
    }


    public boolean attesaAttiva(double disAttivavione){
        Point3D positionPlayer = player.getPosition();
        double dis = position.distance(positionPlayer);
        return disAttivavione >= dis;
    }

    public void subisci(int danno) {
        puntiVita -= danno;
        if(puntiVita <= 0) {
            setTranslateY(0.7);
            Sounds.playSound(Sounds.DEATH);
        }
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
