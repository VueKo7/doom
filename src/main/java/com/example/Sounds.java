package com.example;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Sounds {
    
    //effetti
    public static final String SHOOT = "/sounds/shoot.mp3";
    public static final String WALK = "/sounds/walk.mp3";
    public static final String DEATH = "/sounds/death.mp3";
    
    //soundtrack
    public static final String MENU = "/sounds/menu.mp3";
    public static final String GAME = "/sounds/game.mp3";


    private static Media media;
    private static MediaPlayer mediaPlayer;


    //riproduce il file mp3, possibilmente uno delle costanti
    public static void playSound(String ASOUND) {

        try {
            media = new Media(new File(ASOUND).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            

        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
