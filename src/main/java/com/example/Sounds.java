package com.example;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Sounds {
    
    //effetti
    public static final String SHOOT = "src/main/resources/sounds/shoot.mp3";
    public static final String WALK = "src/main/resources/sounds/walk.mp3";
    public static final String DEATH = "src/main/resources/sounds/death.mp3";
    
    //soundtrack
    public static final String MENU = "src/main/resources/sounds/menu.mp3";
    public static final String GAME = "src/main/resources/sounds/game.mp3";


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
