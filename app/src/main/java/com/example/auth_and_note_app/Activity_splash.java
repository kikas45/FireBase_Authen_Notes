package com.example.auth_and_note_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_splash extends AppCompatActivity {

    private FirebaseUser firebaseUser;

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler(Looper.getMainLooper());

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (firebaseUser == null){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }


            }
        }, 1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
    }
}