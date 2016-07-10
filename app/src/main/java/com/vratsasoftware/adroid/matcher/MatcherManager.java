package com.vratsasoftware.adroid.matcher;

import com.vratsasoftware.adroid.matcher.GameView.GameManager;

/**
 * Created by Deydo on 10-Jul-16.
 */
public class MatcherManager implements GameManager {

    @Override
    public int[][] resolveBoard(int clickedX, int clickedY, int[][] board) {
        if(isPartOfGroup(clickedX, clickedY, board)) {
            board = removeItem(clickedX, clickedY, board, board[clickedX][clickedY]);
        }
        return board;
    }

    private int[][] removeItem(int currentX, int currentY, int[][] board, int color) {
        if(currentX < 0 || currentX >= board.length
                || currentY < 0 || currentY >= board[0].length) {
            return board;
        }

        if(board[currentX][currentY] != -1 && board[currentX][currentY] == color) {
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
        if(color == -1) return false;

        if(clickedX - 1 >= 0 && board[clickedX - 1][clickedY] == color) {
            return true;
        }
        if(clickedY - 1 >= 0 && board[clickedX][clickedY - 1] == color) {
            return true;
        }
        if(clickedX + 1 < board.length && board[clickedX + 1][clickedY] == color) {
            return true;
        }
        if(clickedY + 1 < board[0].length && board[clickedX][clickedY + 1] == color) {
            return true;
        }
        return false;
    }

    private boolean isLastMove(int[][] board) {
        return false;
    }
}
