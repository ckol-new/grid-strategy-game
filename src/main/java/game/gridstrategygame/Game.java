package game.gridstrategygame;

import game.gridstrategygame.Controller.GameController;
import game.gridstrategygame.Controller.MenuController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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
    public void setSceneGame() {
        // load controller and scene
        FXMLLoader loader;
        GameController gameController;
        Parent root;
        Scene gameScene;
        try {
            loader = new FXMLLoader(Game.class.getResource("/game/gridstrategygame/game.fxml"))   ;
            root = loader.load();
            gameController = loader.getController();
            gameScene = new Scene(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // pass controller instance of game
        gameController.setGameInstance(this);
        gameController.setRoot((AnchorPane) root);

        // set stage
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }

    // close
    public void closeGame() {
        Platform.exit();
    }
}
