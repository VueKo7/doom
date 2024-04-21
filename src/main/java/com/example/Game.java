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
        // primaryStage.setFullScreen(true);
        

        world = new Group();
        scene = new Scene(world, WIDTH, HEIGHT, true);
        scene.setFill(Color.LIGHTBLUE);

        //creazione player e aggiunta camera alla scena
        camera = new PerspectiveCamera(true);
        scene.setCamera(camera);

        input = new HandleInput(scene);
        player = new Player(camera, input);
        
        //creazione terreno
        terrein = new Box(150, 1, 150);
        terrein.setTranslateY(-1); // Posiziona il terreno sotto il giocatore
        
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

    
}
