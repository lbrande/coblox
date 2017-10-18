package se.kth.coblox.gui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import se.kth.coblox.Game;

public class GameScene extends Scene {
  private GameCanvas canvas;
  private Game game;

  public GameScene(Parent root) {
    super(root);
    canvas = (GameCanvas) lookup("#canvas");
  }

  public void startGame() {
    game = new Game();
    canvas.draw(game);
  }
}
