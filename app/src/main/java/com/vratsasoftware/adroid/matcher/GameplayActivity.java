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
            }
        });
        ((GameView) findViewById(R.id.game_view)).setGameManager(new MatcherManager());
    }

    private double getTime(){
        double time = endTime - startTime;
        return time / 1000;
    }


}
