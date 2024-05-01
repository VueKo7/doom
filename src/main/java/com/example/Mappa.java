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

    private Box box;
    private PhongMaterial material;

    public Mappa(String nomeLvl) {

        // material = new PhongMaterial();
        // material.setDiffuseMap(new Image(getClass().getResourceAsStream("/textures/floor.jpg")));

        // for(int i = 0; i < 20; i++) {
        //     for(int j = 0; j < 20; j++) {
        //         //creazione terreno
        //         box = new Box(5, 1, 5);
        //         box.setTranslateY(-1); // Posiziona il terreno sotto il giocatore
        //         box.setTranslateX(i*5 -35); //poszione in X
        //         box.setTranslateZ(j*5 -5); //posizione in Y

        //         box.setMaterial(material);
                
        //         getChildren().addAll(box);
        //     }
        // }
        loadLvl(nomeLvl);
    }
    

    private void loadLvl(String nomeLvl) {

        try {
            //lettura da file
            Scanner scanner = new Scanner(new FileInputStream("C:\\Users\\gabri\\Desktop\\repos\\doom\\mappa.txt"));

            //carico tutta la matrice da file
            char[][] matrix = new char[WIDTH][HEIGHT];
            for(int i = 0; i < HEIGHT; i++) {
                String line = scanner.nextLine();
                for(int j = 0; j < WIDTH; j++) {
                    matrix[i][j] = line.toCharArray()[j];
                }
            }

            //quando leggo 0 creo una Box(5, 1, 5) texture:floor.jpg
            //quando leggo 1 creo una Box(5, 10, 5) texture:wall.jpg

            for(int i = 0; i < HEIGHT; i++) {
                for(int j = 0; j < WIDTH; j++) {
                    if(matrix[i][j] == '0') { 
                        //impostazione texture
                        material = new PhongMaterial();
                        material.setDiffuseMap(new Image(getClass().getResourceAsStream("/textures/floor.jpg")));

                        //crezione terreno
                        box = new Box(5, 1, 5); //dimensioni X:5 Y:1 Z:5
                        box.setTranslateY(-1); // Posiziona il terreno sotto il giocatore
                        box.setTranslateX(i*5 -35); //poszione in X
                        box.setTranslateZ(j*5 -5); //posizione in Y

                        box.setMaterial(material);

                        //aggiungo la box all'ambiente 3D
                        getChildren().addAll(box);

                    } else {
                        //impostazione texture
                        material = new PhongMaterial();
                        material.setDiffuseMap(new Image(getClass().getResourceAsStream("/textures/wall.jpg")));

                        for(int y = 0; y <= 10; y+=5) {
                            //crezione muro
                            box = new Box(5, 5, 5); //dimensioni X:5 Y:10 Z:5
                            box.setTranslateY(-y); // Posiziona il terreno sotto il giocatore
                            box.setTranslateX(i*5 -35); //poszione in X
                            box.setTranslateZ(j*5 -5); //posizione in Y

                            box.setMaterial(material);

                            //aggiungo la box all'ambiente 3D
                            getChildren().addAll(box);
                        }

                    }
                    
                }
            }

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
