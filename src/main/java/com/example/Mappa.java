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

    public Mappa(String nomeLvl) {

        mappa = new ArrayList<>();
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

            //quando leggo 0 creo una Box(5, 1, 5) texture:floor.jpg
            //quando leggo 1 creo una Box(5, 5, 5) texture:wall.jpg

            for(int i = 0; i < mappa.size(); i++) {
                for(int j = 0; j < mappa.get(i).length(); j++) {
                    if(mappa.get(i).toCharArray()[j] == '0') { 
                        //impostazione texture
                        PhongMaterial material = new PhongMaterial();
                        material.setDiffuseMap(new Image(
                            getClass().getResourceAsStream("/textures/floor.jpg")));

                        //crezione terreno
                        Box box = new Box(5, 1, 5); //dimensioni X:5 Y:1 Z:5
                        box.setId("0");
                        box.setTranslateY(+1); // Posiziona il terreno sotto il giocatore
                        box.setTranslateX(i*5); //poszione in X
                        box.setTranslateZ(j*5); //posizione in Y

                        box.setMaterial(material);

                        //aggiungo la box all'ambiente 3D
                        getChildren().add(box);

                    } else if(mappa.get(i).toCharArray()[j] == '1') {
                        //impostazione texture
                        PhongMaterial material = new PhongMaterial();
                        material.setDiffuseMap(new Image(
                            getClass().getResourceAsStream("/textures/wall.jpg")));

                        //creo più blocchi impilati
                        for(int y = 0; y < 3; y++) {
                            //crezione muro
                            Box box = new Box(5, 5, 5); //dimensioni X:5 Y:10 Z:5
                            box.setId("1");
                            box.setTranslateY(-y*5); // posizioni i blocchi sopra il terreno ogni -5
                            box.setTranslateX(i*5); //poszione in X
                            box.setTranslateZ(j*5); //posizione in Y

                            box.setMaterial(material);

                            //aggiungo la box all'ambiente 3D
                            getChildren().add(box);
                        }

                    }
                } 
            }   //fine crezione Boxes

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }


    @SuppressWarnings("exports")
    public Point3D setEntityPosition(Box entity) {
        Point3D startingPosition = null;
        //scorro tutta la mappa
        for (int i = 0; i < mappa.size(); i++) {
            for (int j = 0; j < mappa.get(i).length(); j++) {
                //filtro per player tramite id e char mappato == 2
                if(mappa.get(i).toCharArray()[j] == '2' && entity.getId().equals("2")) {
                    //nuova posizione player, spawn point
                    startingPosition = new Point3D(j*5, -5, j*5);
                }
                //filtro per mostro tramite id e char mappato == 3
                else if(mappa.get(i).toCharArray()[j] == '3' && entity.getId().equals("3")) {
                    //nuova posizione mostro, spawn point
                    startingPosition = new Point3D(j*5, -5, j*5);
                }
            }
        }
        return startingPosition;    
    }
    
}
