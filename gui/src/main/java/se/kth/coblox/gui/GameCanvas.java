package se.kth.coblox.gui;

import java.util.HashMap;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import se.kth.coblox.Game;

public class GameCanvas extends Canvas {
  private static final Color BACKGROUND_COLOR = Color.BLACK;
  private static HashMap<se.kth.coblox.Color, Color> blockColors;

  public GameCanvas() {
    populateBlockColors();
  }

  public GameCanvas(double width, double height) {
    super(width, height);
    populateBlockColors();
  }

  private void populateBlockColors() {
    blockColors = new HashMap<>();
    blockColors.put(se.kth.coblox.Color.BLACK, Color.DARKGRAY);
    blockColors.put(se.kth.coblox.Color.BLUE, Color.BLUE);
    blockColors.put(se.kth.coblox.Color.GREEN, Color.DARKGREEN);
    blockColors.put(se.kth.coblox.Color.ORANGE, Color.ORANGE);
    blockColors.put(se.kth.coblox.Color.PURPLE, Color.PURPLE);
    blockColors.put(se.kth.coblox.Color.RED, Color.DARKRED);
    blockColors.put(se.kth.coblox.Color.WHITE, Color.LIGHTYELLOW);
  }

  public void draw(Game game) {
    GraphicsContext graphicsContext = getGraphicsContext2D();
    graphicsContext.setFill(BACKGROUND_COLOR);
    graphicsContext.fillRect(0, 0, getWidth(), getHeight());
    double squareSize = getHeight() / game.getBoard().rows();
    /*for (int row = 0; row < game.getBoard().rows(); game.getBoard().rows()) {
      for (int column = 0; column < game.getBoard().columns(); game.getBoard().columns()) {
        graphicsContext.setFill(blockColors.get(game.getBoard().getBlockColor(column, row)));
        graphicsContext.fillRect(
            column * squareSize, getHeight() - (row + 1) * squareSize, squareSize, squareSize);
      }
    }*/
  }
}
