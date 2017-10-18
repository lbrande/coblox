package se.kth.coblox;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Color[][] groundedBlocks;
    private List<Block> fallingPiece;

    public Board() {
        groundedBlocks = new Color[5][15];
        fallingPiece = new ArrayList<>();
    }

    public void generateNewPiece() {
        fallingPiece.add(new Block((int) Math.ceil(columns() / 2), rows() - 1, randomColor()));
    }

    private Color randomColor() {
        return Color.BLACK;
    }

    public int RemoveCompleteRows() {
        return 0;
    }

    public void performTick() {
    }

    public int rows() {
        return groundedBlocks[0].length;
    }

    public int columns() {
        return groundedBlocks.length;
    }
}
