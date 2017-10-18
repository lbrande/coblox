package se.kth.coblox;

public class Game {
  private Board board;
  private int score;

  public Game() {
    score = 0;
  }

  public Board getBoard() {
    return board;
  }

  public int getScore() {
    return score;
  }
}
