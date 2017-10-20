package se.kth.coblox;

public class Game {
  private Board board;
  private int score;

  public Game() {
    board = new Board();
    score = 0;
  }

  public boolean performTick() {
    score += board.removeCompleteRows();
    return board.fallPiece();
  }

  public Board getBoard() {
    return board;
  }

  public int getScore() {
    return score;
  }
}
