package se.kth.coblox.gui;

import java.util.HashMap;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import se.kth.coblox.Game;

public class GameCanvas extends Canvas {
  private static final Color BACKGROUND_COLOR = Color.BLACK;
  private static final Color BORDER_COLOR = Color.WHITE;
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
    blockColors.put(se.kth.coblox.Color.BLACK, Color.DIMGRAY);
    blockColors.put(se.kth.coblox.Color.BLUE, Color.DARKBLUE);
    blockColors.put(se.kth.coblox.Color.GREEN, Color.DARKGREEN);
    blockColors.put(se.kth.coblox.Color.ORANGE, Color.DARKORANGE);
    blockColors.put(se.kth.coblox.Color.PURPLE, Color.PURPLE);
    blockColors.put(se.kth.coblox.Color.RED, Color.DARKRED);
    blockColors.put(se.kth.coblox.Color.WHITE, Color.WHITE);
  }

  public void draw(Game game) {
    GraphicsContext graphicsContext = getGraphicsContext2D();
    graphicsContext.setFill(BACKGROUND_COLOR);
    graphicsContext.fillRect(0, 0, getWidth(), getHeight());
    double squareSize = getHeight() / game.getBoard().rows();
    double startX = (getWidth() - game.getBoard().columns() * squareSize) / 2;
    for (int row = 0; row < game.getBoard().rows(); row++) {
      for (int column = 0; column < game.getBoard().columns(); column++) {
        se.kth.coblox.Color blockColor = game.getBoard().getBlockColor(column, row);
        if (blockColor != null) {
          graphicsContext.setFill(blockColors.get(blockColor));
          graphicsContext.fillRect(
              startX + column * squareSize,
              getHeight() - (row + 1) * squareSize,
              squareSize,
              squareSize);
        }
      }
    }
    graphicsContext.setStroke(BORDER_COLOR);
    graphicsContext.strokeRect(startX, 0, game.getBoard().columns() * squareSize, getHeight());
  }
}
