package com.example;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.robot.Robot;

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
        

        //FIRE WEAPON
        scene.setOnMouseClicked(event -> {
            System.out.println("shoot");
        }); 


        

    }


    public double getRotation() {return rotation;}

    public boolean getKeyState(@SuppressWarnings("exports") KeyCode keyCode) {
        return buffer[keyCode.getCode()];
    }
}
