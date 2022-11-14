package com.example.auth_and_note_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class Utility {
    static void ShowToast(Context context , String message){
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }

    static CollectionReference getCollectionsForNotes(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
       return FirebaseFirestore.getInstance().collection("notes").document(Objects.requireNonNull(firebaseUser.getEmail()))
                .collection("myNotes");
    }

    @SuppressLint("SimpleDateFormat")
    static String TimeStamp_to_String(Timestamp timestamp){
       return new SimpleDateFormat("MM/dd/yyyy" ).format(timestamp.toDate());
    }
}
