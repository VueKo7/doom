package com.example;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public class HandleInput {
    
    private double rotation = 0; //per visuale
    private boolean buffer[];

    public HandleInput(@SuppressWarnings("exports") Scene scene) {
        buffer = new boolean[255];

        //input da tastiera
        scene.setOnKeyPressed(event -> {
            buffer[event.getCode().getCode()] = true;
        });

        scene.setOnKeyReleased(event -> {
            buffer[event.getCode().getCode()] = false;
        });


        //ROTAZIONE VISUALE
        scene.setOnMouseMoved(event -> {
            
            double mouseX = event.getSceneX();
            double centerX = scene.getWidth() / 2.0;
            
            // Calcolo della rotazione in base alla posizione del mouse
            double deltaX = mouseX - centerX;
            double rotationFactor = deltaX / centerX; // Calcolo del fattore di rotazione da -1 a 1
            rotation = -360 * rotationFactor; // La rotazione varia da 0 a 180 gradi 
        });
    

        // scene.setCursor(Cursor.NONE);


        //FIRE WEAPON
        scene.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY) { //filtro l'input
                if(event.getClickCount() < 3) { //tolgo lo spam di click
                    buffer[event.getButton().hashCode() % buffer.length] = true;
                }
            }
        }); 

    }


    public double getRotation() {return rotation;}

    //ottengo lo stato di un'evento dal buffer
    public boolean getKeyState(@SuppressWarnings("exports") KeyCode keyCode) {
        return buffer[keyCode.getCode()];
    }
    public boolean getKeyState(@SuppressWarnings("exports") MouseButton keyCode) {
        return buffer[keyCode.hashCode() % buffer.length];
    }

    //una volta effettuato il click viene riportato a false l'evento
    public void setKeyState(@SuppressWarnings("exports") MouseButton keyCode, boolean bool) {
        buffer[keyCode.hashCode() % buffer.length] = bool;        
    }
    public void setKeyState(@SuppressWarnings("exports") KeyCode keyCode, boolean bool) {
        buffer[keyCode.getCode()] = bool;        
    }
}
