package com.example.auth_and_note_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.auth_and_note_app.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    
    ActivityLoginBinding binding;

    private FirebaseAuth firebaseAuth;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
      //  setContentView(R.layout.activity_login);

        binding.loginAccount.setOnClickListener(v-> loginUser());
        binding.longinTextCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });



    }

    private void loginUser(){
        String email = binding.emailEditTextLogin.getText().toString();
        String password = binding.passwordEditTextLogin.getText().toString();



        boolean isValiDated = valiDatemyIds(email, password);
        // if is Validated is False
        if (!isValiDated) {
            return;
        }



        // Now we can Proceed to Create the FireBase ACCOUNT
        login_Account_In_FireBase(email, password);

    }

private void login_Account_In_FireBase(String email, String password){

        firebaseAuth = FirebaseAuth.getInstance();
        change_In_ProgressBar(true);
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                change_In_ProgressBar(false);

                if (task.isSuccessful()){
                    // login sucessful

                    checkForeAccountVerification();

                }else {
                    // login failed
                    Toast.makeText(getApplicationContext(), ""+ Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    binding.progressbarCreateAccount.setVisibility(View.INVISIBLE);
                    binding.loginAccount.setVisibility(View.VISIBLE);
                }
            }
        });

}

    private void checkForeAccountVerification() {
        if (Objects.requireNonNull(firebaseAuth.getCurrentUser()).isEmailVerified()){
            /// go to Main activity
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(getApplicationContext(), "Please Verify Email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
            binding.progressbarCreateAccount.setVisibility(View.INVISIBLE);
            binding.loginAccount.setVisibility(View.VISIBLE);
        }
    }

    private void change_In_ProgressBar(boolean InProgress) {
        if (InProgress) {
            binding.progressbarCreateAccount.setVisibility(View.VISIBLE);
            binding.loginAccount.setVisibility(View.INVISIBLE);
        } else {
            binding.progressbarCreateAccount.setVisibility(View.INVISIBLE);
            binding.loginAccount.setVisibility(View.INVISIBLE);
        }
    }


    private boolean valiDatemyIds(String email, String pass) {
        //// lwt create a method to Validate the Data, this returns True or False

        // we check if the email is valid or not
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEditTextLogin.setError("Email is Invalid");
            return false;  //  i.e return nothing after the error
        }

        if (pass.length() < 6) {
            binding.passwordEditTextLogin.setError("Password is Invalid");
            return false;  //  i.e return nothing after the error
        }


        //// the (3) three things above is true, then Return TRUE

        return true;
    }



}