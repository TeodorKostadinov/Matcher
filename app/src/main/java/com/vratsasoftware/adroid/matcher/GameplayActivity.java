package com.vratsasoftware.adroid.matcher;

import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.vratsasoftware.adroid.matcher.Database.DatabaseHelper;
import com.vratsasoftware.adroid.matcher.cmn.User;
import com.vratsasoftware.adroid.matcher.game_logic.Block;
import com.vratsasoftware.adroid.matcher.game_logic.GameHelper;

public class GameplayActivity extends AppCompatActivity {

    RelativeLayout relative;
    GameHelper gameHelper;
    TextView txtGameOver;
    Button btnTryAgain;
    DatabaseHelper dbHelper;
    EditText edtName;
    long startTime;
    long endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        Firebase.setAndroidContext(this);

        relative = (RelativeLayout) findViewById(R.id.relative);
        gameHelper = new GameHelper(relative);
        edtName = (EditText) findViewById(R.id.edt_name);
        txtGameOver = (TextView) findViewById(R.id.txt_game_over);
        dbHelper = new DatabaseHelper(this);
        btnTryAgain = (Button) findViewById(R.id.btn_try_again);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtGameOver.setVisibility(View.INVISIBLE);
                btnTryAgain.setVisibility(View.INVISIBLE);
                edtName.setVisibility(View.INVISIBLE);
                btnTryAgain.setClickable(false);
                edtName.setClickable(false);
                final String name = edtName.getText().toString();
                writeRecord(name);
            }
        });
        createNewGame();
    }

    private void writeRecord(final String name){
        AsyncTask <Void, Void, Void> as = new AsyncTask() {
            @Override
            protected Void doInBackground(Object[] params) {
                User user = new User(name, gameHelper.getPoints(), getTime());
                dbHelper.writeToDatabase(user);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                createNewGame();
            }
        }.execute();
    }

    private double getTime(){
        double time = endTime - startTime;
        return time / 1000;
    }

    private void createNewGame(){
        for(int i = 0; i < 36; i++){
            final Button btn = (Button) relative.getChildAt(i);
            btn.setBackgroundResource(Block.getRandomColor());
            btn.setVisibility(View.VISIBLE);
            final int finalI = i;
            final ColorDrawable cd = (ColorDrawable) btn.getBackground();
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gameHelper.removeConnectedBlocks(finalI, cd.getColor());
                    gameHelper.adjustBlocks();
                    if(!gameHelper.checkForPossibleMoves()){
                        endTime = System.currentTimeMillis();
                        if(relative.getChildAt(30).getVisibility() == View.INVISIBLE) {
                            txtGameOver.setText("CONGRATULATIONS!");
                        } else {
                            txtGameOver.setText("GAME OVER!");
                        }
                        txtGameOver.setVisibility(View.VISIBLE);
                        btnTryAgain.setVisibility(View.VISIBLE);
                        edtName.setVisibility(View.VISIBLE);
                        btnTryAgain.setClickable(true);
                        edtName.setClickable(true);
                    }
                }
            });
        }
        startTime = System.currentTimeMillis();
    }
}
