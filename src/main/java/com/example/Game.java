package com.example;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
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
    
    private static Mappa world;
    private Scene scene;

    private Weapon weapon;
    private PerspectiveCamera camera;
    private Player player;
    private HandleInput input;

    private Mostro[] mostri;


    @Override
    public void start(@SuppressWarnings("exports") Stage primaryStage) {
        // primaryStage.setFullScreen(true);

        world = new Mappa("/mappa.txt");
        scene = new Scene(world, WIDTH, HEIGHT, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.LIGHTBLUE);

        makePlayer(); //INIT player
        
        mostri = new Mostro[3];
        for (int i = 0; i < mostri.length; i++) {
            mostri[i] = new Mostro(player, 5,5,5, 0.1, 100, i+30, -5, i+30);
            mostri[i].setId("mostro");
        }
        


        //aggiungo il player, l'arma e il mostro all'ambiente
        world.getChildren().addAll(player, weapon);
        world.getChildren().addAll(mostri);

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

                for(int i = 0; i < mostri.length; i++) {
                    mostri[i].update();
                }
                
            }
        }, 0, FRAME_TIME);
    }


    //inizializzazione player
    public void makePlayer() {
        //creazione player e aggiunta camera alla scena
        camera = new PerspectiveCamera(true);
        input = new HandleInput(scene);
        weapon = new Weapon(15);
        player = new Player(camera, input, weapon);
        scene.setCamera(camera); //aggiungo la camera alla scena

        player.setId("player"); //imposto un id per distinzione
        weapon.setId("weapon"); //imposto un id per distinzione
    }











    public static Mappa getWorld() {
        return world;
    }

    public Player getPlayer() {
        return player;
    }
    
}
