package se.kth.coblox.gui;

import static se.kth.coblox.Direction.LEFT;
import static se.kth.coblox.Direction.RIGHT;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
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
            if (!game.performTick()) {
              timer.cancel();
            }
            Platform.runLater(() -> canvas.draw(game));
          }
        },
        0,
        500);
    canvas.requestFocus();
  }

  public void keyPressed(KeyEvent keyEvent) {
    switch (keyEvent.getCode()) {
      case W:
      case UP:
        game.getBoard().rotatePiece(RIGHT);
        canvas.draw(game);
        break;
      case S:
      case DOWN:
        game.performTick();
        canvas.draw(game);
        break;
      case A:
      case LEFT:
        game.getBoard().movePiece(LEFT);
        canvas.draw(game);
        break;
      case D:
      case RIGHT:
        game.getBoard().movePiece(RIGHT);
        canvas.draw(game);
        break;
    }
  }
}
