package com.example;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class Game extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Timer timer;
    private static final int FPS = 30; // Frame per secondo
    private static final long FRAME_TIME = 1000 / FPS; // Tempo in millisecondi per frame


    private static final double WIDTH = 1280;
    private static final double HEIGHT = 720;
    
    private PerspectiveCamera camera;
    private Group world;
    private Scene scene;
    private Player player;
    private Box terrein;
    private HandleInput input;
    


    @Override
    public void start(@SuppressWarnings("exports") Stage primaryStage) {

        world = new Group();
        scene = new Scene(world, WIDTH, HEIGHT, true);
        scene.setFill(Color.LIGHTBLUE);

        //creazione player e aggiunta camera alla scena
        camera = new PerspectiveCamera(true);
        scene.setCamera(camera);

        input = new HandleInput(scene);
        player = new Player(camera, input);
        
        //creazione terreno
        terrein = new Box(100, 1, 100);
        terrein.setTranslateY(0); // Posiziona il terreno sotto il giocatore
        terrein.setMaterial(new PhongMaterial(Color.GREEN)); // Colore verde per il terreno

        world.getChildren().addAll(player, terrein);

        primaryStage.setTitle("First Person Camera");
        primaryStage.setScene(scene);
        primaryStage.show();

        //gameloop
        timer = new Timer();
        start();

        //chiusura programma
        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });
    }



    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Qui si aggiorna e si disegna il gioco
                player.update();
            }
        }, 0, FRAME_TIME);
    }


    
    
    
    

    



    // private void movePlayer(double direction) {
    //     double playerDeltaX = Math.sin(Math.toRadians(playerRotateY)) * direction;
    //     double playerDeltaZ = Math.cos(Math.toRadians(playerRotateY)) * direction;
    //     double newX = playerX + playerDeltaX;
    //     double newZ = playerZ + playerDeltaZ;

    //     // Controllo per evitare che il giocatore esca dal terreno
    //     if (Math.abs(newX) < 200 && Math.abs(newZ) < 200) {
    //         playerX = newX;
    //         playerZ = newZ;
    //     }

    //     updateCamera();
    // }

    // private void rotatePlayer(double direction) {
    //     playerRotateY += direction * 5;
    //     updateCamera();
    // }

    

    
}
