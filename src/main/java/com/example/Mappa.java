package com.example;


import java.util.ArrayList;
import java.util.Scanner;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;


public class Mappa extends Group {

    private ArrayList<String> mappa;
    private ArrayList<Point3D> monsters_spawnPoints;
    private Point3D player_spawnPoint;

    public Mappa(String nomeLvl) {

        mappa = new ArrayList<>();
        monsters_spawnPoints = new ArrayList<>();
        player_spawnPoint = null;
        loadLvl(nomeLvl);
    }
    

    private void loadLvl(String nomeLvl) {

        try {
            //lettura da file
            Scanner scanner = new Scanner(getClass().getResourceAsStream(nomeLvl));

            //carico tutta la matrice da file
            while(scanner.hasNext()) {
                mappa.add(scanner.nextLine());
            }
            //fine lettura
            scanner.close();

            //quando leggo ' ' creo una Box(5, 1, 5) texture:floor.jpg
            //quando leggo 1 creo una Box(5, 5, 5) texture:wall.jpg

            for(int i = 0; i < mappa.size(); i++) {
                for(int j = 0; j < mappa.get(i).length(); j++) {
                    //creazione pavimento
                    if(mappa.get(i).toCharArray()[j] == ' ') { 
                        //creo la box 5*5*5 che compone il pavimento
                        Box box = makeBox(i*5, 0, j*5, 5, 1, 5, "/textures/floor.jpg", " ");
                        //lo aggiungo all'ambiente 3D
                        getChildren().add(box);
                    } 
                    //creazione muri
                    else if(mappa.get(i).toCharArray()[j] == '1') {
                        //creo più blocchi impilati
                        for(int y = 0; y < 4; ++y) {
                            //creo 4 muri impilati uno sopra l'altro
                            Box box = makeBox(i*5, -y*5, j*5, 5, 5, 5, "/textures/wall.jpg", "1");
                            //aggiungo la box all'ambiente 3D
                            getChildren().add(box);
                        }
                    } 
                    //posizione player
                    else if(mappa.get(i).toCharArray()[j] == '2') {
                        //filtro per player tramite id e char mappato == 2
                        if(mappa.get(i).toCharArray()[j] == '2') {
                            Box box = makeBox(i*5, 0, j*5, 5, 1, 5, "/textures/floor.jpg", " ");
                            //chiudo il buco rimasto dal player
                            getChildren().add(box);
                            //nuova posizione player, spawn point
                            player_spawnPoint = new Point3D(i*5, -5, j*5);
                        }
                    }
                    //posizione mostro
                    else if(mappa.get(i).toCharArray()[j] == '3') {
                        Box box = makeBox(i*5, 0, j*5, 5, 1, 5, "/textures/floor.jpg", " ");
                        //chiudo il buco rimasto dai mostri
                        getChildren().add(box);
                        //nuova posizione mostro, spawn point
                        monsters_spawnPoints.add(new Point3D(i*5, -4, j*5));
                    }
                } 
            }   //fine crezione Boxes

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
    @SuppressWarnings("exports")
    public Box makeBox(int x, int y, int z, int w, int h, int d, String textPath, String id) {
        //impostazione texture
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(
            getClass().getResourceAsStream(textPath)));

        //crezione terreno
        Box box = new Box(w, h, d); //dimensioni X:5 Y:1 Z:5
        box.setId(id); //imposto l'ID a quello di parametro
        box.setTranslateY(y); // Posiziona il terreno sotto il giocatore
        box.setTranslateX(x); //poszione in X
        box.setTranslateZ(z); //posizione in Y

        box.setMaterial(material);

        return box;
    }


    @SuppressWarnings("exports")
    public ArrayList<Point3D> getMonsters_spawnPoints() {
        return monsters_spawnPoints;
    }

    @SuppressWarnings("exports")
    public Point3D getPlayer_spawnPoint() {
        return player_spawnPoint;
    }

}
