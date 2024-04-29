package com.example;


import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Mappa extends Group {

    private Box terrein;

    public Mappa(String nomeLvl) {

        //creazione terreno
        terrein = new Box(150, 1, 150);
        terrein.setTranslateY(-1); // Posiziona il terreno sotto il giocatore
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("/textures/floor.jpg")));
        terrein.setMaterial(material);
        
        getChildren().addAll(terrein);


        // loadLvl(nomeLvl);
    }
    

    private void loadLvl(String nomeLvl) {

        






    }
}
