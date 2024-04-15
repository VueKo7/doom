package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Camera;
import javafx.scene.Group;
// import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
// import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
// import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private double mousePosX, mousePosY;
    private double mouseOldX, mouseOldY;
    private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);


    public static int SIZEFACTOR = 100;

    private static Scene scene;
    private static FXMLLoader fxmlLoader;

    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException {
        // scene = new Scene(loadFXML("primary"), 640, 480);
        // PrimaryController primaryController = fxmlLoader.getController();

        // scene.setCamera(primaryController.getCamera());
        // stage.setScene(scene);

        Group world = createEnvironment();
        
        Scene scene = new Scene(world);
        
        stage.setScene(scene);
        stage.setWidth(16 * SIZEFACTOR);
        stage.setHeight(9 * SIZEFACTOR);

        Camera camera = new PerspectiveCamera();
        camera.setFarClip(2000);
        camera.setNearClip(1);

        scene.setCamera(camera);

        

        // Translate worldTransX = new Translate();

        // world.getTransforms().addAll(worldRotY, worldRotX);

        

        scene.setOnMouseMoved(event -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
            double dx = (mousePosX - mouseOldX);
            double dy = (mousePosY - mouseOldY);
            double modifier = 1.0;
            double modifierFactor = 0.2;
            if (event.isControlDown()) {
                modifier = 0.1;
            }
            rotateY.setAngle(rotateY.getAngle() - dx*modifierFactor*modifier);
            rotateX.setAngle(rotateX.getAngle() + dy*modifierFactor*modifier);
        });

        camera.getTransforms().addAll(rotateX, rotateY);

        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch(event.getCode()){
                
                case W: //w/s is for z
                    camera.setTranslateZ(camera.getTranslateZ() - 10);
                    break;
                case S:
                    camera.setTranslateZ(camera.getTranslateZ() + 10);
                    break;
                case A:// a/d is x axis
                    camera.setTranslateX(camera.getTranslateX() - 10);
                    break;
                case D:
                    camera.setTranslateX(camera.getTranslateX() + 10);
                    break;
                case X:// shift/contr is for y axis
                    camera.setTranslateY(camera.getTranslateY() - 10);
                    break;
                case C:
                    camera.setTranslateY(camera.getTranslateY() + 10);
                    break;

                default:
                    break;
            }
        });

        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private Group createEnvironment(){
        Group group = new Group();

        Box ground = new Box();
        ground.setHeight(100);
        ground.setWidth(100);
        ground.setDepth(100);

        ground.setTranslateX(200);
        ground.setTranslateZ(200);

        group.getChildren().addAll(ground);

        return group;
    }

    public static void main(String[] args) {
        launch();
    }

}