package ua.itea.ijavaadv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;


/**
 * Created
 * at 19:34
 * on 08.03.17
 * by Iurii Derevianko;
 *
 */

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {

    // load FXML
    Parent rootFXMLParent;
    final String MAIN_FXML_FILE = "main.fxml";
    FXMLLoader loader = new FXMLLoader();
    URL mainFxml = getClass().getClassLoader().getResource(MAIN_FXML_FILE);
        loader.setLocation(mainFxml);
        try {
            rootFXMLParent = loader.load();
        }
        catch(IOException exception) {
            throw new RuntimeException(exception);
        }

        // tune up a stage
        primaryStage.setTitle("usr-desktop");
        Scene primaryScene = new Scene(rootFXMLParent);
        //primaryScene.getAccelerators().put(new KeyCodeCombination(KeyCode.X), primaryStage::hide);
        primaryScene.setOnKeyPressed(this::closePrimaryStageOnEscape);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
        primaryStage.setResizable(true);
        primaryStage.setMinHeight(680);
        primaryStage.setMinWidth(900);
        this.primaryStage = primaryStage;

}

    public void closePrimaryStageOnEscape(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            primaryStage.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

