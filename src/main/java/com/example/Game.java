package com.example;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.stage.Stage;

public class Game extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Timer timer;
    private static final int FPS = 34; // Frame per secondo
    private static final long FRAME_TIME = 1000 / FPS; // Tempo in millisecondi per frame


    private static final double WIDTH = 1280;
    private static final double HEIGHT = 720;
    
    private Weapon weapon;
    private PerspectiveCamera camera;
    private static Mappa world;
    private Scene scene;
    private Player player;
    private HandleInput input;


    @Override
    public void start(@SuppressWarnings("exports") Stage primaryStage) {
        // primaryStage.setFullScreen(true);

        world = new Mappa("/mappa.txt");
        scene = new Scene(world, WIDTH, HEIGHT, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.LIGHTBLUE);

        //creazione player e aggiunta camera alla scena
        camera = new PerspectiveCamera(true);
        scene.setCamera(camera); //aggiungo la camera alla scena

        input = new HandleInput(scene);
        weapon = new Weapon(15);
        player = new Player(camera, input, weapon);

        player.setId("player");
        weapon.setId("weapon");
        
        //aggiungo il player all'ambiente
        world.getChildren().addAll(player, weapon);

        primaryStage.setTitle("DOOM");
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
                // aggiornamento singoli elementi
                player.update();
            }
        }, 0, FRAME_TIME);
    }

    public static Mappa getWorld() {
        return world;
    }
    
}
