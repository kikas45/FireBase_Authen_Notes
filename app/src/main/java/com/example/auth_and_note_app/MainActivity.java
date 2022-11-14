package com.example.auth_and_note_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.auth_and_note_app.databinding.ActivityLoginBinding;
import com.example.auth_and_note_app.databinding.ActivityMainBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    FirebaseAuth firebaseAuth;

    NoteAdapterClass noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        firebaseAuth = FirebaseAuth.getInstance();
        String name = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();

        binding.userTextView.setText(name);
        binding.floatingActionButton.setOnClickListener(v-> goToDetailsActivity());

        binding.menuMain.setOnClickListener(v->showMyPopMenu());

        setUpRecyclerView();

        binding.menuMain.setOnClickListener(v -> showMyPopMenu());


    }

    private void setUpRecyclerView() {

        Query query = Utility.getCollectionsForNotes().orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<NotesModel> options = new FirestoreRecyclerOptions.Builder<NotesModel>()
                .setQuery(query, NotesModel.class).build();
        binding.recycclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        noteAdapter = new NoteAdapterClass(options, getApplicationContext());
        binding.recycclerView.setAdapter(noteAdapter);

    }

    private void showMyPopMenu() {

   /*     PopupMenu popupMenu = new PopupMenu(getApplicationContext(), binding.menuMain);
        popupMenu.getMenu().add("Log out");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle() == "Log out"){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                    return true;
                }
                return false;
            }
        });
*/

        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), binding.menuMain);
        popupMenu.getMenu().add("Log out").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                return false;
            }
        });
        popupMenu.show();


    }

    private void goToDetailsActivity() {
        Intent intent = new Intent(getApplicationContext(), NoteDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();


            noteAdapter.stopListening();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        noteAdapter.notifyDataSetChanged();
    }
}