package se.kth.coblox;

public class Game {
  private Board board;
  private int score;

  public Game() {
    board = new Board();
    score = 0;
  }

  public void performTick() {

  }

  public Board getBoard() {
    return board;
  }

  public int getScore() {
    return score;
  }
}