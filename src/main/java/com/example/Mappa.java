package com.example;


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

        loadLvl(nomeLvl);
        
        
    }
    

    private void loadLvl(String nomeLvl) {

        try {
            //lettura da file
            Scanner scanner = new Scanner(getClass().getResourceAsStream(nomeLvl));

            //carico tutta la matrice da file
            char[][] matrix = new char[WIDTH][HEIGHT];
            for(int i = 0; i < HEIGHT; i++) {
                String line = scanner.nextLine();
                for(int j = 0; j < WIDTH; j++) {
                    matrix[i][j] = line.toCharArray()[j];
                }
            }

            //fine lettura
            scanner.close();

            //quando leggo 0 creo una Box(5, 1, 5) texture:floor.jpg
            //quando leggo 1 creo una Box(5, 5, 5) texture:wall.jpg

            for(int i = 0; i < HEIGHT; i++) {
                for(int j = 0; j < WIDTH; j++) {
                    if(matrix[i][j] == '0') { 
                        //impostazione texture
                        material = new PhongMaterial();
                        material.setDiffuseMap(new Image(getClass().getResourceAsStream("/textures/floor.jpg")));

                        //crezione terreno
                        box = new Box(5, 1, 5); //dimensioni X:5 Y:1 Z:5
                        box.setId("0");
                        box.setTranslateY(+1); // Posiziona il terreno sotto il giocatore
                        box.setTranslateX(i*5 -35); //poszione in X
                        box.setTranslateZ(j*5 -5); //posizione in Y

                        box.setMaterial(material);

                        //aggiungo la box all'ambiente 3D
                        getChildren().add(box);

                    } else if(matrix[i][j] == '1') {
                        //impostazione texture
                        material = new PhongMaterial();
                        material.setDiffuseMap(new Image(getClass().getResourceAsStream("/textures/wall.jpg")));

                        //creo più blocchi impilati
                        for(int y = 0; y <= 10; y+=5) {
                            //crezione muro
                            box = new Box(5, 5, 5); //dimensioni X:5 Y:10 Z:5
                            box.setId("1");
                            box.setTranslateY(-y); // posizioni i blocchi sopra il terreno ogni -5
                            box.setTranslateX(i*5 -35); //poszione in X
                            box.setTranslateZ(j*5 -5); //posizione in Y

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


    
    
}
