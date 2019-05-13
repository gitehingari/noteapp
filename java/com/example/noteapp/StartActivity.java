package com.example.noteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.noteapp.user_sign.LoginActivity;
import com.example.noteapp.user_sign.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {
    private Button btnReg,btnLog;
    private FirebaseAuth fAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        findIDs();
        fAuth=FirebaseAuth.getInstance();
        updateUI();

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();

            }
        });
    }

    private void register(){
        startActivity(new Intent(StartActivity.this, RegisterActivity.class));

    }

    private void login(){

        startActivity(new Intent(StartActivity.this, LoginActivity.class));

    }
    private void updateUI(){
        if (fAuth.getCurrentUser() != null){
            Log.i("StartActivity","fAuth != null");
            Intent startIntent = new Intent(StartActivity.this,MainActivity.class);
            startActivity(startIntent);
            finish();

        }
        else{

            Log.i("StartActivity","fAuth = null");

        }
    }


    private void findIDs(){
        btnLog=findViewById(R.id.start_log_button);
        btnReg=findViewById(R.id.start_reg_button);
    }
}
