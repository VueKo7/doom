package com.example;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.geometry.Point3D;
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
                // aggiornamento player
                player.update();
                //aggiornamento mostri
                mostri.forEach(mostro -> {
                    mostro.update();
                });
                
            }
        }, 0, FRAME_TIME);
    }





    //inizializzazione mostri
    private void makeMonsters() {
        //inizializzo l'array
        mostri = new ArrayList<>();
        //creo i mostri e li aggiungo all'array
        Mostro mostro = new Mostro(
            player, 5,5,5, 0.1, 100, 0, -5, 0);
        mostro.setId("3");
        Point3D pos = world.setEntityPosition(mostro);
        if(pos != null) {
            mostro.setPosition(pos);
            mostri.add(mostro);
        } 

        System.out.println(mostri.size());
    }


    //inizializzazione player
    private void makePlayer() {
        //creazione player e aggiunta camera alla scena
        camera = new PerspectiveCamera(true);
        input = new HandleInput(scene);
        weapon = new Weapon(15);
        player = new Player(camera, input, weapon);
        scene.setCamera(camera); //aggiungo la camera alla scena

        player.setId("2"); //imposto un id per distinzione
        weapon.setId("2"); //imposto un id per distinzione

        Point3D pos = world.setEntityPosition(player);
        if(pos != null) player.setPosition(pos);
    }











    public static Mappa getWorld() {
        return world;
    }

    public Player getPlayer() {
        return player;
    }
    
}
