package se.kth.coblox.gui;

import javafx.scene.input.KeyEvent;

public class GUIController {
  public GameCanvas canvas;

  public void keyPressed(KeyEvent keyEvent) {
    ((GameScene) canvas.getScene()).keyPressed(keyEvent);
  }
}
