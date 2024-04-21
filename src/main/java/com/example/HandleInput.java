package com.example;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.robot.Robot;

public class HandleInput {
    
    private double rotation = 0;
    Robot robot;

    private boolean buffer[];

    public HandleInput(@SuppressWarnings("exports") Scene scene) {
        buffer = new boolean[255];
        robot = new Robot();


        //input da tastiera
        scene.setOnKeyPressed(event -> {
            buffer[event.getCode().getCode()] = true;
        });

        scene.setOnKeyReleased(event -> {
            buffer[event.getCode().getCode()] = false;
        });


        //l'input del mouse
        scene.setOnMouseMoved(event -> {
            double mouseX = event.getSceneX();
            double centerX = scene.getWidth() / 2.0;
            
            // Calcolo della rotazione in base alla posizione del mouse
            double deltaX = mouseX - centerX;
            double rotationFactor = deltaX / centerX; // Calcolo del fattore di rotazione da -1 a 1
            rotation = -360 * rotationFactor; // La rotazione varia da 0 a 180 gradi 
            
            
        }); 
    }


    public double getRotation() {return rotation;}

    public boolean getKeyState(@SuppressWarnings("exports") KeyCode keyCode) {
        return buffer[keyCode.getCode()];
    }
}
