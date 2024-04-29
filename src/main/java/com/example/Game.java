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
    private static final int FPS = 60; // Frame per secondo
    private static final long FRAME_TIME = 1000 / FPS; // Tempo in millisecondi per frame


    private static final double WIDTH = 1280;
    private static final double HEIGHT = 720;
    
    private Weapon weapon;
    private PerspectiveCamera camera;
    private Group world;
    private Scene scene;
    private Player player;
    private HandleInput input;

    //TODO IMPOSTARE UNA CLASSE MAPPA PER GLI ELEMENTI Terrein e World

    @Override
    public void start(@SuppressWarnings("exports") Stage primaryStage) {
        // primaryStage.setFullScreen(true);

        world = new Mappa("livello1.txt");
        scene = new Scene(world, WIDTH, HEIGHT, true);
        scene.setFill(Color.LIGHTBLUE);

        //creazione player e aggiunta camera alla scena
        camera = new PerspectiveCamera(true);
        scene.setCamera(camera); //aggiungo la camera alla scena

        input = new HandleInput(scene);
        weapon = new Weapon(15);
        player = new Player(camera, input, weapon);
        
        
        //aggiungo il player all'ambiente
        world.getChildren().addAll(player,  weapon);

        primaryStage.setTitle("First Person Camera");
        primaryStage.setScene(scene);
        primaryStage.show();

        //gameloop
        timer = new Timer();
        start();

        //chiusura programma
        primaryStage.
        setOnCloseRequest(event -> {
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

    
}
