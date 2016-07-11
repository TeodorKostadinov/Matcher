package com.vratsasoftware.adroid.matcher;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.vratsasoftware.adroid.matcher.cmn.Block;

import java.util.Random;

/**
 * Created by Deydo on 09-Jul-16.
 */
public class GameView extends View {

    private static final int EMPTY = Block.COLOR_NONE;

    private int[] COLORS;
    private Block[][] board;
    private int boardX;
    private int boardY;
    private GameManager gameManager;

    private Paint paint;

    //Sizes
    private int widthPx;
    private int heightPx;
    private int boxSizePx;

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        COLORS = new int[] {
            getContext().getResources().getColor(R.color.blue),
                getContext().getResources().getColor(R.color.red),
                getContext().getResources().getColor(R.color.green),
                getContext().getResources().getColor(R.color.yellow)
        };
        boxSizePx = getContext().getResources().getDimensionPixelSize(R.dimen.box_size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);
    }

    private void drawBoard(Canvas canvas) {
        for (int x = 0; x < boardX; x++) {
            for (int y = 0; y < boardY; y++) {
                Block block = board[x][y];
                if(block.color != EMPTY) {
                    paint.setColor(block.color);
                    canvas.drawRect(block.visibleX * boxSizePx, block.visibleY * boxSizePx, (block.visibleX + 1) * boxSizePx, (block.visibleY + 1) * boxSizePx, paint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            handleTouch((int) event.getX() / boxSizePx, (int) event.getY() / boxSizePx);
        }
        return super.onTouchEvent(event);
    }

    private void handleTouch(int x, int y) {
        if(board[x][y].color != EMPTY) {
            if(gameManager != null) {
                board = gameManager.resolveBoard(x, y, board);
            }
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        widthPx = w;
        heightPx = h;
        boardX = widthPx / boxSizePx;
        boardY = heightPx / boxSizePx;
        if(board == null) {
            initBoard();
        }
    }

    private void initBoard() {
        Random rnd = new Random();
        board = new Block[boardX][boardY];
        for (int x = 0; x < boardX; x++) {
            for (int y = 0; y < boardY; y++) {
                int randomColorIndex = Math.abs(rnd.nextInt() % COLORS.length);
                Block block = new Block();
                block.color = COLORS[randomColorIndex];
                block.x = x;
                block.y = y;
                block.visibleX = x;
                block.visibleY = y;
                board[x][y] = block;
            }
        }
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public interface GameManager {
        Block[][] resolveBoard(int clickedX, int clickedY, Block[][] board);
    }
}
