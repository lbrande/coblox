package se.kth.coblox;

import java.util.*;

public class Board {
  private Color[][] groundedBlocks;
  private List<Block> fallingPiece;
  private Block rotatorPiece;
  private Random random;

  public Board() {
    groundedBlocks = new Color[15][5];
    fallingPiece = new ArrayList<>();
    rotatorPiece = null;
    random = new Random();
  }

  public void generateNewPiece(int numberOfBlocks) {
    if (numberOfBlocks >= 1) {
      for (int i = 1; i <= numberOfBlocks; i++) {
        fallingPiece.add(new Block((int) Math.ceil(rows() / 2), columns() - i, randomColor()));
        if (i == 1) {
          rotatorPiece = fallingPiece.get(0);
        }
      }
    }
  }

  private Color randomColor() {
    return Color.values()[random.nextInt(Color.values().length)];
  }

  public int removeCompleteRows() {
    int score = 0;
    for (int row = 0; row < rows(); row++) {
      int allSimilar = allSimilar(row);
      int allDifferent = allDifferent(row);
      if (allSimilar > 0 || allDifferent > 0) {
        if (allSimilar > 0) {
          score += allSimilar;
        } else {
          score += allDifferent;
        }
        Arrays.fill(groundedBlocks[row], null);
        dropDownRows(row);
      }
      row--;
    }
    return score;
  }

  private void dropDownRows(int deletedRow) {
    for (int column = 0; column < columns(); column++) {
      for (int row = deletedRow; deletedRow < rows() - 1; deletedRow++) {
        groundedBlocks[row][column] = groundedBlocks[row + 1][column];
      }
    }
  }

  private int allDifferent(int row) {
    int score = 0;
    for (int column = 0; column < columns(); column++) {
      if (groundedBlocks[row][column] == null || groundedBlocks[row][column].equals(Color.BLACK)) {
        return -1;
      } else if (groundedBlocks[row][column].equals(Color.WHITE)) {
        score += 5;
        continue;
      }

      for (int i = 0; i < column; i++) {
        if (groundedBlocks[row][i].equals(groundedBlocks[row][column])) {
          return -1;
        }
      }
      score += 10;
    }
    return score;
  }

  private int allSimilar(int row) {
    int score = 0;
    Color firstColoredBlock = null;
    for (int column = 0; column < columns(); column++) {
      if (groundedBlocks[row][column] == null) {
        return -1;
      } else if (groundedBlocks[row][column].equals(Color.WHITE)) {
        score += 5;
        continue;
      } else if (groundedBlocks[row][column].equals(Color.BLACK)) {
        score += 25;
        continue;
      } else if (firstColoredBlock == null) {
        firstColoredBlock = groundedBlocks[row][column];
        score += 15;
        continue;
      }

      if (groundedBlocks[row][column].equals(firstColoredBlock)) {
        score += 15;
      } else {
        score = 0;
        break;
      }
    }
    return score;
  }

  public void fallPiece() {
    Iterator<Block> piece = fallingPiece.iterator();
    while (piece.hasNext()) {
      Block block = piece.next();
      if (groundedBlocks[block.getY() - 1][block.getX()] != null) {
        block.setY(block.getY() - 1);
      } else {
        groundedBlocks[block.getY()][block.getX()] = block.getColor();
        fallingPiece.remove(block);
        if (fallingPiece.size() == 0) {
          generateNewPiece(2);
        }
      }
    }
  }

  public boolean movePiece(Direction direction) {
    boolean isMovable = true;
    for (Block block : fallingPiece) {
      if (direction == Direction.LEFT) {
        if (block.getX() == 0 || groundedBlocks[block.getY()][block.getX() - 1] != null) {
          isMovable = false;
          break;
        }
      } else if (direction == Direction.RIGHT) {
        if (block.getX() == columns() - 1
            || groundedBlocks[block.getY()][block.getX() + 1] != null) {
          isMovable = false;
          break;
        }
      }
    }

    if (isMovable) {
      for (Block block : fallingPiece) {
        if (direction == Direction.LEFT) {
          block.setX(block.getX() - 1);
        } else if (direction == Direction.RIGHT) {
          block.setX(block.getX() - 1);
        }
      }
    }

    return isMovable;
  }

  public boolean rotatePiece(Direction direction) {
    /*if (fallingPiece.size() > 1) {
      int[] relativePieceCoordinate = {
        fallingPiece.get(1).getY() - rotatorPiece.getY(),
        fallingPiece.get(1).getX() - rotatorPiece.getX()
      };
      if (direction == Direction.LEFT) {
        if (relativePieceCoordinate[0] == 1 && relativePieceCoordinate[1] == 0) {}
      }
    }*/
    return false;
  }

  public int rows() {
    return groundedBlocks.length;
  }

  public int columns() {
    return groundedBlocks[0].length;
  }
}
