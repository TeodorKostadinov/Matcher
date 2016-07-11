package com.vratsasoftware.adroid.matcher;

import com.vratsasoftware.adroid.matcher.GameView.GameManager;
import com.vratsasoftware.adroid.matcher.cmn.Block;

/**
 * Created by Deydo on 10-Jul-16.
 */
public class MatcherManager implements GameManager {

    private OnGameEndListener listener;

    @Override
    public Block[][] resolveBoard(int clickedX, int clickedY, Block[][] board) {
        if (isPartOfGroup(clickedX, clickedY, board)) {
            board = removeItem(clickedX, clickedY, board, board[clickedX][clickedY].color);
            board = applyGravity(board);
            handleLastMove(board);
        }
        return board;
    }

    private void handleLastMove(Block[][] board) {
        if (!hasMoves(board) && listener != null) {
            listener.onGameEnded(isBoardEmpty(board));
        }
    }

    private boolean hasMoves(Block[][] board) {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if(isPartOfGroup(x, y, board)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isBoardEmpty(Block[][] board) {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y].color != -1) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setOnGameEndListener(OnGameEndListener listener) {
        this.listener = listener;
    }

    private Block[][] applyGravity(Block[][] board) {
        for (int i = 0; i < board[0].length; i++) {

            for (int x = board.length - 1; x >= 0; x--) {
                for (int y = board[0].length - 1; y >= 0; y--) {
                    if (board[x][y].color == -1 && y - 1 >= 0) {
//                        board[x][y] = board[x][y - 1];
//                        board[x][y - 1].color = -1;

                        Block topBlock = board[x][y - 1];
                        board[x][y].x = topBlock.x;
                        board[x][y].y = topBlock.y;

                    }
                }
            }
        }

        //Remove empty columns
        for (int i = 0; i < board.length; i++) {
            for (int x = 0; x < board.length; x++) {
                if (board[x][board[0].length - 1].color == -1 && x + 1 < board.length) {
                    Block[] swap = board[x];
                    board[x] = board[x + 1];
                    board[x + 1] = swap;
                }
            }
        }
        return board;
    }

    private Block[][] removeItem(int currentX, int currentY, Block[][] board, int color) {
        if (currentX < 0 || currentX >= board.length
                || currentY < 0 || currentY >= board[0].length) {
            return board;
        }

        if (board[currentX][currentY].color != -1 && board[currentX][currentY].color == color) {
            board[currentX][currentY].color = -1;
            board = removeItem(currentX - 1, currentY, board, color);
            board = removeItem(currentX + 1, currentY, board, color);
            board = removeItem(currentX, currentY - 1, board, color);
            board = removeItem(currentX, currentY + 1, board, color);
        }
        return board;
    }

    private boolean isPartOfGroup(int clickedX, int clickedY, Block[][] board) {
        int color = board[clickedX][clickedY].color;
        if (color == -1) return false;

        if (clickedX - 1 >= 0 && board[clickedX - 1][clickedY].color == color) {
            return true;
        }
        if (clickedY - 1 >= 0 && board[clickedX][clickedY - 1].color == color) {
            return true;
        }
        if (clickedX + 1 < board.length && board[clickedX + 1][clickedY].color == color) {
            return true;
        }
        if (clickedY + 1 < board[0].length && board[clickedX][clickedY + 1].color == color) {
            return true;
        }
        return false;
    }

    public interface OnGameEndListener {
        void onGameEnded(boolean isSuccess);
    }
}
