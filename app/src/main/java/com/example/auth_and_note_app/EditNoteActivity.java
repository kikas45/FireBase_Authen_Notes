package com.example.auth_and_note_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.auth_and_note_app.databinding.ActivityEditNoteBinding;
import com.example.auth_and_note_app.databinding.ActivityNoteDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class EditNoteActivity extends AppCompatActivity {

    ActivityEditNoteBinding binding;

    String title, content, docId;

    boolean isEditMode = false;

    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");


        binding.addTitle.setText(title);
        binding.addContent.setText(content);

        docId = getIntent().getStringExtra("docId");

        binding.pageTitle.setText("Edit your Note");


        binding.saveNotes.setOnClickListener(v -> saveNotes());

        binding.backpressImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });


        binding.deletNote.setOnClickListener(v -> deleNotesFromFireBase());


    }


    private void saveNotes() {

        Toast.makeText(getApplicationContext(), "Note Saved", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

        String noteTitles = binding.addTitle.getText().toString();
        String noteContenst = binding.addContent.getText().toString();

        if (noteTitles.isEmpty()) {
            binding.addTitle.setError("Please Add Title");
        }

        if (noteContenst.isEmpty()) {
            binding.addContent.setError("Please Add content");
        }

        // instead of an Hash Map
        NotesModel notesModel = new NotesModel();
        notesModel.setTitle(noteTitles);
        notesModel.setContent(noteContenst);
        notesModel.setTimestamp(Timestamp.now());

        saveNoteToFireBase(notesModel);

    }

    private void saveNoteToFireBase(NotesModel notesModel) {

        if (!isEditMode) {
            /// edit the notes
            documentReference = Utility.getCollectionsForNotes().document(docId);

            documentReference.set(notesModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Utility.ShowToast(getApplicationContext(), "Note added successful");
                    } else {
                        Utility.ShowToast(getApplicationContext(), "Failed, Try again");
                    }
                }
            });

        }
    }

    private void deleNotesFromFireBase() {

        documentReference = Utility.getCollectionsForNotes().document(docId);
        Utility.ShowToast(getApplicationContext(), "Done");
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Utility.ShowToast(getApplicationContext(), "Note Deleted Successfully");
                } else {
                    Utility.ShowToast(getApplicationContext(), "Failed, Try again");
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
