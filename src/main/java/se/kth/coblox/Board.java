package se.kth.coblox;

import java.awt.image.ByteLookupTable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {
  private static int SCORE_FOR_DIFFERENT = 10;
  private static int SCORE_FOR_SIMILAR = 15;

  private Color[][] groundedBlocks;
  private List<Block> fallingPiece;
  private Block rotatorPiece;
  private Random random;

  public Board() {
    groundedBlocks = new Color[15][5];
    fallingPiece = new ArrayList<>();
    rotatorPiece = null;
    random = new Random();

    generateNewPiece(2);
  }

  public boolean generateNewPiece(int numberOfBlocks) {
    if (numberOfBlocks > 0 && numberOfBlocks < columns()) {
      for (int i = 1; i <= numberOfBlocks; i++) {
        int x = (int) Math.ceil(columns() / 2);
        int y = rows() - i;
        if (groundedBlocks[y][x] == null) {
          fallingPiece.add(new Block(x, y, randomColor()));
        } else {
          return false;
        }
        if (i == 1) {
          rotatorPiece = fallingPiece.get(0);
        }
      }
      return true;
    } else {
      throw new IndexOutOfBoundsException("0 < numberOfBlocks < columns");
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
        row--;
      }
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
        return 0;
      } else if (groundedBlocks[row][column].equals(Color.WHITE)) {
        score += SCORE_FOR_DIFFERENT / 2;
        continue;
      }

      for (int i = 0; i < column; i++) {
        if (groundedBlocks[row][i].equals(groundedBlocks[row][column])) {
          return 0;
        }
      }
      score += SCORE_FOR_DIFFERENT;
    }
    return score;
  }

  private int allSimilar(int row) {
    int score = 0;
    Color firstColoredBlock = null;
    for (int column = 0; column < columns(); column++) {
      if (groundedBlocks[row][column] == null) {
        return 0;
      } else if (groundedBlocks[row][column].equals(Color.WHITE)) {
        score += SCORE_FOR_SIMILAR / 3;
        continue;
      } else if (groundedBlocks[row][column].equals(Color.BLACK)) {
        score += SCORE_FOR_SIMILAR * 2;
        continue;
      } else if (firstColoredBlock == null) {
        firstColoredBlock = groundedBlocks[row][column];
        score += SCORE_FOR_SIMILAR;
        continue;
      }

      if (groundedBlocks[row][column].equals(firstColoredBlock)) {
        score += SCORE_FOR_SIMILAR;
      } else {
        return 0;
      }
    }
    return score;
  }

  public boolean fallPiece() {
    List<Block> collidingBlocks = new ArrayList<>();
    for (Block block : fallingPiece) {
      if (block.getY() == 0 || groundedBlocks[block.getY() - 1][block.getX()] != null) {
        collidingBlocks.add(block);
      }
    }

    for (Block collidingBlock : collidingBlocks) {
      List<Block> fallingPieceCopy = new ArrayList<>(fallingPiece);
      for (Block fallingBlock : fallingPieceCopy) {
        if (fallingBlock.getX() == collidingBlock.getX()) {
          groundedBlocks[fallingBlock.getY()][fallingBlock.getX()] = fallingBlock.getColor();
          fallingPiece.remove(fallingBlock);
        }
      }
    }

    if (fallingPiece.size() > 0) {
      for (Block block : fallingPiece) {
        block.setY(block.getY() - 1);
      }
      return true;
    } else {
      return generateNewPiece(2);
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
          block.setX(block.getX() + 1);
        }
      }
    }

    return isMovable;
  }

  public boolean rotatePiece(Direction directionOfRotation) {
    List<Block> oldFallingPiece = fallingPiece;
    boolean isRotating = true;
    if (fallingPiece.size() > 1) {
      int[] relativePieceCoordinate = {
        fallingPiece.get(1).getY() - rotatorPiece.getY(),
        fallingPiece.get(1).getX() - rotatorPiece.getX()
      };

      for (Block block : fallingPiece) {
        if (block == rotatorPiece) {
          continue;
        }

        int index = fallingPiece.indexOf(block);

        int newRelativeX = 0;
        int newRelativeY = 0;

        if (directionOfRotation == Direction.RIGHT) {
          int sinOfNewCoordinate =
              (int) Math.sin(Math.asin(relativePieceCoordinate[0]) - Math.PI / 2);
          newRelativeY =
              relativePieceCoordinate[1] == -1 ? -sinOfNewCoordinate : sinOfNewCoordinate;

          int cosOfNewCoordinate =
              (int) Math.cos(Math.acos(relativePieceCoordinate[1]) - Math.PI / 2);
          newRelativeX =
              relativePieceCoordinate[1] == -1 ? -cosOfNewCoordinate : cosOfNewCoordinate;

        } else if (directionOfRotation == Direction.LEFT) {
          int sinOfNewCoordinate =
              (int) Math.sin(Math.asin(relativePieceCoordinate[0]) + Math.PI / 2);
          newRelativeY =
              relativePieceCoordinate[1] == -1 ? -sinOfNewCoordinate : sinOfNewCoordinate;

          int cosOfNewCoordinate =
              (int) Math.cos(Math.acos(relativePieceCoordinate[1]) + Math.PI / 2);
          newRelativeX =
              relativePieceCoordinate[1] == -1 ? -cosOfNewCoordinate : cosOfNewCoordinate;
        }

        if (groundedBlocks[block.getY() + newRelativeY][block.getX() + newRelativeX] != null
            || block.getX() == columns() - 1 && newRelativeX == 1
            || block.getY() == rows() - 1 && newRelativeY == 1
            || block.getX() == 0 && newRelativeX == -1
            || block.getY() == 0 && newRelativeY == -1) {
          isRotating = false;
        }

        if (isRotating) {
          block.setX(block.getX() + newRelativeX * index);
          block.setY(block.getY() + newRelativeY * index);
        } else {
          fallingPiece = oldFallingPiece;
          break;
        }
      }
    } else {
      isRotating = false;
    }
    return isRotating;
  }

  public int rows() {
    return groundedBlocks.length;
  }

  public int columns() {
    return groundedBlocks[0].length;
  }
}
