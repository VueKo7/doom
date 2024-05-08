package com.example;

import java.util.ArrayList;
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
    private static final int FPS = 60; // Frame per secondo
    private static final long FRAME_TIME = 1000 / FPS; // Tempo in millisecondi per frame

    private static final double WIDTH = 1280;
    private static final double HEIGHT = 720;
    
    private static Mappa world;
    private Scene scene;

    private Weapon weapon;
    private PerspectiveCamera camera;
    private Player player;
    private HandleInput input;

    private ArrayList<Mostro> mostri;


    @Override
    public void start(@SuppressWarnings("exports") Stage primaryStage) {
        // primaryStage.setFullScreen(true);
        
        world = new Mappa("/mappa.txt");
        scene = new Scene(world, WIDTH, HEIGHT, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.LIGHTBLUE);

        makePlayer(); //INIT player
        makeMonsters(); //INIT monsters

        //aggiungo gli elementi dinamici al mondo
        world.getChildren().addAll(player, weapon); //aggiungo il player
        world.getChildren().addAll(mostri); //aggiungo i mostri

        primaryStage.setTitle("DOOM");//zola consiglia di cambiare nome
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


    //gameloop
    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //aggiornamento mostri
                mostri.forEach(mostro -> {
                    if(mostro.getPuntiVita() > 0)
                        mostro.update();
                });
                // aggiornamento player
                player.update();
            }
        }, 0, FRAME_TIME);
    }


    //inizializzazione mostri
    private void makeMonsters() {
        //inizializzo l'array
        mostri = new ArrayList<>();
        //creo i mostri e li aggiungo all'array
        world.getMonsters_spawnPoints().forEach(monster_spawnPoint -> {
            int x = (int)monster_spawnPoint.getX();
            int y = (int)monster_spawnPoint.getY();
            int z = (int)monster_spawnPoint.getZ();
            Mostro mostro = new Mostro( //creo mostro nella posizione di spawn
                player, 5,5,5, 0.4, 60, x, y, z);
            mostro.setId("3"); //identificativo per mostro
            //aggiungo il mostro alla lista
            mostri.add(mostro);
        });
    }


    //inizializzazione player
    private void makePlayer() {
        //creazione player e aggiunta camera alla scena
        camera = new PerspectiveCamera(true);
        input = new HandleInput(scene);
        weapon = new Weapon(15, 20);
        player = new Player(camera, input, weapon, 0, -5, 0);
        scene.setCamera(camera); //aggiungo la camera alla scena

        player.setId("2"); //imposto un id per distinzione
        weapon.setId("2"); //imposto un id per distinzione

        //imposto la posizione del player al suo spawnPoint
        player.setPosition(world.getPlayer_spawnPoint());
    }


    public static Mappa getWorld() {return world;}

    public Player getPlayer() {return player;}
}
