package com.vratsasoftware.adroid.matcher.game_logic;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
public class GameHelper {

    RelativeLayout relative;

    public GameHelper(RelativeLayout relative){
        this.relative = relative;
    }

    private boolean moveIsLegal(int pos, int color){
        ColorDrawable cd;
        if(isInside(pos - 6) && relative.getChildAt(pos - 6).getVisibility() == View.VISIBLE){
            cd = (ColorDrawable) relative.getChildAt(pos - 6).getBackground();
            if(cd.getColor() == color) {
                return true;
            }
        }
        if(isInside(pos + 6) && relative.getChildAt(pos + 6).getVisibility() == View.VISIBLE){
            cd = (ColorDrawable) relative.getChildAt(pos + 6).getBackground();
            if(cd.getColor() == color){
                return true;
            }
        }
        if(isInside(pos + 1) && (pos + 1) % 6 != 0 && relative.getChildAt(pos + 1).getVisibility() == View.VISIBLE){
            cd = (ColorDrawable) relative.getChildAt(pos + 1).getBackground();
            if(cd.getColor() == color){
                return true;
            }
        }
        if(isInside(pos - 1) && (pos % 6 != 0) && relative.getChildAt(pos - 1).getVisibility() == View.VISIBLE){
            cd = (ColorDrawable) relative.getChildAt(pos - 1).getBackground();
            if(cd.getColor() == color){
                return true;
            }
        }
        return false;
    }

    public int getPoints(){
        int points = 0;
        Button btn;
        for(int i = 0; i < 36; i++){
            btn = (Button) relative.getChildAt(i);
            if(btn.getVisibility() == View.INVISIBLE){
                points++;
            }
        }
        return points;
    }

    public boolean checkForPossibleMoves(){
        ColorDrawable cd;
        for(int i = 0; i < 36; i++){
            if(relative.getChildAt(i).getVisibility() == View.VISIBLE) {
                cd = (ColorDrawable) relative.getChildAt(i).getBackground();
                if (moveIsLegal(i, cd.getColor())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void removeConnectedBlocks(int pos, int color){
        if(moveIsLegal(pos, color)){
            findConnectivity(pos, color);
        }
    }

    private void findConnectivity(int pos, int color){
        if(!isInside(pos)){
            return;
        }
        ColorDrawable cd = (ColorDrawable) relative.getChildAt(pos).getBackground();
        if(cd.getColor() != color || relative.getChildAt(pos).getVisibility() == View.INVISIBLE){
            return;
        }
        relative.getChildAt(pos).setVisibility(View.INVISIBLE);

        if((pos + 1) % 6 != 0){
            findConnectivity(pos + 1, color);
        }
        if((pos % 6 != 0)){
            findConnectivity(pos - 1, color);
        }
        findConnectivity(pos - 6, color);
        findConnectivity(pos + 6, color);
    }

    private boolean isInside(int pos){
        return pos >= 0 && pos < 36 ? true : false;
    }

    public void adjustBlocks(){
        Button btn;
        ColorDrawable cd;
        for(int i = 0; i < 36; i++){
            btn = (Button) relative.getChildAt(i);
            if(btn.getVisibility() == View.INVISIBLE){
                cd = (ColorDrawable) btn.getBackground();
                fallDownOneLine(i, btn, cd);
            }
        }
        removeAllEmptyColumns();
    }

    private void removeAllEmptyColumns() {
        Button btn;
        ColorDrawable cd;
        for(int i = 35; i >= 30; i--){
            if(relative.getChildAt(i).getVisibility() == View.INVISIBLE){
                btn = (Button) relative.getChildAt(i - 30);
                cd = (ColorDrawable) btn.getBackground();
                removeEmptyColumn(i - 30, btn, cd, i - 30);
            }
        }
    }

    private void fallDownOneLine(int pos, Button btn, ColorDrawable cd){
        if(isInside(pos - 6)){
            if(relative.getChildAt(pos - 6).getVisibility() == View.INVISIBLE){
                btn.setVisibility(View.INVISIBLE);
                return;
            }
            cd = (ColorDrawable) relative.getChildAt(pos - 6).getBackground();
            btn.setVisibility(View.VISIBLE);
            btn.setBackgroundColor(cd.getColor());
            btn = (Button) relative.getChildAt(pos - 6);
            btn.setVisibility(View.INVISIBLE);
            fallDownOneLine(pos - 6, btn, cd);
        }
    }

    private void removeEmptyColumn(int pos, Button btn, ColorDrawable cd, int firstPos){
        if(isInside(pos + 1)){
            if((pos + 1) % 6 == 0){
                firstPos += 6;
                pos = firstPos;
                btn = (Button) relative.getChildAt(firstPos);
                removeEmptyColumn(pos, btn, cd, firstPos);
            }
            else {
                if (relative.getChildAt(pos + 1).getVisibility() == View.VISIBLE) {
                    cd = (ColorDrawable) relative.getChildAt(pos + 1).getBackground();
                    btn.setVisibility(View.VISIBLE);
                    btn.setBackgroundColor(cd.getColor());
                }
                btn = (Button) relative.getChildAt(pos + 1);
                btn.setVisibility(View.INVISIBLE);
                removeEmptyColumn(pos + 1, btn, cd, firstPos);
            }
        }
    }
}
