package com.example.auth_and_note_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;
import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText email_editText, password_editText, comfirm_editText;
    private Button btn_create_account;
    private TextView longin_text;
    private ProgressBar progressbar_create_account;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        email_editText = findViewById(R.id.email_editText);
        password_editText = findViewById(R.id.password_editText);
        comfirm_editText = findViewById(R.id.comfirm_editText);
        btn_create_account = findViewById(R.id.btn_create_account);
        longin_text = findViewById(R.id.longin_text);
        progressbar_create_account = findViewById(R.id.progressbar_create_account);

        btn_create_account.setOnClickListener(view -> createAccount());
        longin_text.setOnClickListener(view -> goTocreateAccount());



    }

    private void goTocreateAccount() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


    private void createAccount() {

        String email = email_editText.getText().toString();
        String password = password_editText.getText().toString();
        String confirmPassowrd = comfirm_editText.getText().toString();


        boolean isValiDated = valiDatemyIds(email, password, confirmPassowrd);

        // if is Validated is False

        if (!isValiDated) {
            return;
        }

        // Now we can Proceed to Create the FireBase ACCOUNT

        create_My_Account_In_FireBase(email, password);

    }

    private void create_My_Account_In_FireBase(String email, String password) {
        change_In_ProgressBar(true);
        firebaseAuth = FirebaseAuth.getInstance();

        // Okay, Now let Create the Account

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Account Created Successfully", Toast.LENGTH_SHORT).show();
                    // firebaseAuth.getCurrentUser().sendEmailVerification(); /// we send the email verification

                    Objects.requireNonNull(firebaseAuth.getCurrentUser()).sendEmailVerification(); /// we send the email verification

                    firebaseAuth.signOut();  // We sign Out the User and Allow the User to Sign in only When Verified


                    //  Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    //   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    // startActivity(intent);

                    finish(); // finish this activity


                } else {

                    Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    btn_create_account.setVisibility(View.VISIBLE);
                    progressbar_create_account.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void change_In_ProgressBar(boolean InProgress) {
        if (InProgress) {
            progressbar_create_account.setVisibility(View.VISIBLE);
            btn_create_account.setVisibility(View.INVISIBLE);
        } else {
            progressbar_create_account.setVisibility(View.INVISIBLE);
            btn_create_account.setVisibility(View.INVISIBLE);
        }
    }


    private boolean valiDatemyIds(String email, String pass, String confirm_pass) {
        //// lwt create a method to Validate the Data, this returns True or False

        // we check if the email is valid or not
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_editText.setError("Email is Invalid");
            return false;  //  i.e return nothing after the error
        }

        if (pass.length() < 6) {
            password_editText.setError("Password is Invalid");
            return false;  //  i.e return nothing after the error
        }

        if (!pass.equals(confirm_pass)) {
            comfirm_editText.setError("Password do not Match");
            return false;
        }

        //// the (3) three things above is true, then Return TRUE

        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}