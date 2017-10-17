package se.kth.coblox.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI.fxml"));
    Parent root = fxmlLoader.load();
    primaryStage.setScene(new Scene(root));
    primaryStage.sizeToScene();
    primaryStage.show();
  }
}
