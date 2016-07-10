package com.vratsasoftware.adroid.matcher;

import com.vratsasoftware.adroid.matcher.GameView.GameManager;

/**
 * Created by Deydo on 10-Jul-16.
 */
public class MatcherManager implements GameManager {

    private OnGameEndListener listener;

    @Override
    public int[][] resolveBoard(int clickedX, int clickedY, int[][] board) {
        if (isPartOfGroup(clickedX, clickedY, board)) {
            board = removeItem(clickedX, clickedY, board, board[clickedX][clickedY]);
            board = applyGravity(board);
            handleLastMove(board);
        }
        return board;
    }

    private void handleLastMove(int[][] board) {
        if (!hasMoves(board) && listener != null) {
            listener.onGameEnded(isBoardEmpty(board));
        }
    }

    private boolean hasMoves(int[][] board) {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if(isPartOfGroup(x, y, board)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isBoardEmpty(int[][] board) {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] != -1) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setOnGameEndListener(OnGameEndListener listener) {
        this.listener = listener;
    }

    private int[][] applyGravity(int[][] board) {
        for (int i = 0; i < board[0].length; i++) {

            for (int x = board.length - 1; x >= 0; x--) {
                for (int y = board[0].length - 1; y >= 0; y--) {
                    if (board[x][y] == -1 && y - 1 >= 0) {
                        board[x][y] = board[x][y - 1];
                        board[x][y - 1] = -1;
                    }
                }
            }
        }

        //Remove empty columns
        for (int i = 0; i < board.length; i++) {
            for (int x = 0; x < board.length; x++) {
                if (board[x][board[0].length - 1] == -1 && x + 1 < board.length) {
                    int[] swap = board[x];
                    board[x] = board[x + 1];
                    board[x + 1] = swap;
                }
            }
        }
        return board;
    }

    private int[][] removeItem(int currentX, int currentY, int[][] board, int color) {
        if (currentX < 0 || currentX >= board.length
                || currentY < 0 || currentY >= board[0].length) {
            return board;
        }

        if (board[currentX][currentY] != -1 && board[currentX][currentY] == color) {
            board[currentX][currentY] = -1;
            board = removeItem(currentX - 1, currentY, board, color);
            board = removeItem(currentX + 1, currentY, board, color);
            board = removeItem(currentX, currentY - 1, board, color);
            board = removeItem(currentX, currentY + 1, board, color);
        }
        return board;
    }

    private boolean isPartOfGroup(int clickedX, int clickedY, int[][] board) {
        int color = board[clickedX][clickedY];
        if (color == -1) return false;

        if (clickedX - 1 >= 0 && board[clickedX - 1][clickedY] == color) {
            return true;
        }
        if (clickedY - 1 >= 0 && board[clickedX][clickedY - 1] == color) {
            return true;
        }
        if (clickedX + 1 < board.length && board[clickedX + 1][clickedY] == color) {
            return true;
        }
        if (clickedY + 1 < board[0].length && board[clickedX][clickedY + 1] == color) {
            return true;
        }
        return false;
    }

    public interface OnGameEndListener {
        void onGameEnded(boolean isSuccess);
    }
}
