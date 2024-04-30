package com.example;


import java.io.FileInputStream;
import java.util.Scanner;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Mappa extends Group {

    public final int WIDTH = 20;
    public final int HEIGHT = 20;

    private Box terrein;

    public Mappa(String nomeLvl) {

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("/textures/floor.jpg")));

        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                //creazione terreno
                terrein = new Box(5, 1, 5);
                terrein.setTranslateY(-1); // Posiziona il terreno sotto il giocatore
                terrein.setTranslateX(i*5 -35); //poszione in X
                terrein.setTranslateZ(j*5 -5); //posizione in Y

                terrein.setMaterial(material);
                
                getChildren().addAll(terrein);
            }
        }

        // loadLvl(nomeLvl);
    }
    

    private void loadLvl(String nomeLvl) {

        try {
            //lettura da file
            Scanner scanner = new Scanner(new FileInputStream(nomeLvl));

            //carico tutta la matrice da file
            char[][] line = new char[WIDTH][HEIGHT];
            for(int i = 0; i < WIDTH; i++) {
                for(int j = 0; j < HEIGHT; j++) {
                    line[i] = scanner.nextLine().toCharArray();
                }
            }
            
            //quando leggo 0 creo una Box(5, 1, 5) texture:floor.jpg
            //quando leggo 1 creo una Box(5, 10, 5) texture:wall.jpg

            for(int i = 0; i < line.length; i++) {
                if(line[i] == '0') { 
                    //crezione terreno
                    terrein = new Box(5, 1, 5);
                    terrein.setTranslateY(-1); // Posiziona il terreno sotto il giocatore
                    terrein.setTranslateX(i*5 -35); //poszione in X
                    terrein.setTranslateZ(j*5 -5); //posizione in Y

                    terrein.setMaterial(material);

                } else {
                    //crezione terreno

                }
                getChildren().addAll(terrein);
            }



            scanner.close();

        } catch (Exception e) {

        }
        





    }
}
