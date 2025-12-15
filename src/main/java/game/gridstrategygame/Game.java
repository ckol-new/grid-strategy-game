package game.gridstrategygame;

import game.gridstrategygame.Controller.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

public class Game extends Application {
    Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        setSceneMenu();
    }

    // set scene menu
    public void setSceneMenu() {
        // load controller and scene
        FXMLLoader loader;
        MenuController menuController;
        Parent root;
        Scene menuScene;
        try {
            loader = new FXMLLoader(Game.class.getResource("/game/gridstrategygame/menu.fxml"))   ;
            root = loader.load();
            menuController = loader.getController();
            menuScene = new Scene(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // pass controller instance of game
        menuController.setGameInstance(this);

        // set stage
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    // set scene game
}
