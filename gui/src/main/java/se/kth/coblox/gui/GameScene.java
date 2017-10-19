package se.kth.coblox.gui;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import se.kth.coblox.Game;

public class GameScene extends Scene {
  private GameCanvas canvas;
  private Timer timer;
  private Game game;

  public GameScene(Parent root) {
    super(root);
    canvas = (GameCanvas) lookup("#canvas");
    timer = new Timer(true);
  }

  public void startGame() {
    game = new Game();
    timer.schedule(
        new TimerTask() {
          @Override
          public void run() {
            System.out.println("hello");
            game.performTick();
            Platform.runLater(() -> canvas.draw(game));
          }
        },
        0,
        1000);
  }
}
