package se.kth.coblox.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import se.kth.coblox.Game;

public class GameCanvas extends Canvas {
  private static final Color BACKGROUND_COLOR = Color.BLACK;

  public void draw(Game game) {
    GraphicsContext graphicsContext = getGraphicsContext2D();
    graphicsContext.setFill(BACKGROUND_COLOR);
    graphicsContext.fillRect(0, 0, getWidth(), getHeight());
  }
}
