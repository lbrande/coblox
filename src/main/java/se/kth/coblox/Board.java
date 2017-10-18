package se.kth.coblox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {
    private Color[][] groundedBlocks;
    private List<Block> fallingPiece;
    private Random random;

    public Board() {
        groundedBlocks = new Color[15][5];
        fallingPiece = new ArrayList<>();
        random = new Random();
    }

    public void generateNewPiece(int numberOfBlocks) {
        if (numberOfBlocks >= 1) {
            for (int i = 1; i <= numberOfBlocks; i++) {
                fallingPiece.add(new Block((int) Math.ceil(rows() / 2), columns() - i, randomColor()));
            }
        }
    }

    private Color randomColor() {
        return Color.values()[random.nextInt(Color.values().length)];
    }

    public int removeCompleteRows() {
        int score = 0;
        for (int row = 0; row < rows(); row++) {
            if (allSimilar(row) > 0) {
                score += allSimilar(row);
                Arrays.fill(groundedBlocks[row], null);
                dropDownRows(row);
            } else if (allDifferent(row) > 0) {
                score += allDifferent(row);
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

    /**
     * private boolean isAllSimilar(int row){ int similarPieces = 0; for (int column = 0; column <
     * columns(); column++) {
     * <p>
     * <p>
     * <p>
     * <p>} return false; }
     */
    public void fallPiece() {
    }

    public int rows() {
        return groundedBlocks.length;
    }

    public int columns() {
        return groundedBlocks[0].length;
    }
}
