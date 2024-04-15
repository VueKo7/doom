package com.example;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.PerspectiveCamera;
import javafx.scene.input.KeyEvent;

public class PrimaryController implements Initializable {
    
    private double velocityX;
    private double velocityY;
   
    @FXML
    private PerspectiveCamera camera;

    
    @FXML
    private void movement(ActionEvent event) {

        switch (((KeyEvent)event.getSource()).getCharacter().charAt(0)) {
            case 'w':
                camera.setTranslateX(velocityX-=1);
                break;
            case 'a':
                camera.setTranslateY(velocityY-=1);
                break;
            case 's':
                camera.setTranslateX(velocityX+=1);
                break;
            case 'd':
                camera.setTranslateY(velocityY+=1);
                break;
        }

        System.out.printf("[X:%d - Y:%d - Z:%d]/n", (int)camera.getTranslateX(), (int)camera.getTranslateY(), (int)camera.getTranslateZ());
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        camera.setTranslateX(100);

        velocityX = camera.getTranslateX();
        velocityY = camera.getTranslateY();

        
        System.out.println("PrimaryController Class");
        System.out.printf("[X:%d - Y:%d - Z:%d]/n", (int)camera.getTranslateX(), (int)camera.getTranslateY(), (int)camera.getTranslateZ());
    }




    @SuppressWarnings("exports")
    public PerspectiveCamera getCamera() {
        return camera;
    }
}
