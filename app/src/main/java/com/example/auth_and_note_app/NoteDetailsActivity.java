package com.example.auth_and_note_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.auth_and_note_app.databinding.ActivityNoteDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.util.Objects;

public class NoteDetailsActivity extends AppCompatActivity {

    ActivityNoteDetailsBinding binding;

    String title, content, docId;

    boolean isEditMode = false;

    DocumentReference documentReference;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.saveNotes.setOnClickListener(v->saveNotes());

        binding.backpressImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });



    }



    private void saveNotes() {

        Toast.makeText(getApplicationContext(), "Note Saved", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

        String   noteTitles = binding.addTitle.getText().toString();
        String noteContenst = binding.addContent.getText().toString();

        if (noteTitles.isEmpty()){
            binding.addTitle.setError("Please Add Title");
        }

        if (noteContenst.isEmpty()){
            binding.addContent.setError("Please Add content");
        }

        // instead of an Hash Map
        NotesModel notesModel = new NotesModel();
        notesModel.setTitle(noteTitles);
        notesModel.setContent(noteContenst);
        notesModel.setTimestamp(Timestamp.now());

        saveNoteToFireBase(notesModel);

    }

    private void saveNoteToFireBase(NotesModel notesModel ){

        documentReference = Utility.getCollectionsForNotes().document();

        /// instead of has map
        documentReference.set(notesModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Utility.ShowToast(getApplicationContext(), "Note Saving Successful");
                } else {

                    Utility.ShowToast(getApplicationContext(), "Failed to Save Note");
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}