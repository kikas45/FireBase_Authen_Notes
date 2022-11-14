package com.example.auth_and_note_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteAdapterClass extends FirestoreRecyclerAdapter<NotesModel, NoteAdapterClass.NoteViewHolder> {

    Context context;

    public NoteAdapterClass(@NonNull FirestoreRecyclerOptions<NotesModel> options , Context context) {
        super(options);
        this.context = context;
    }

    @NonNull
    @Override
    public NoteAdapterClass.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_notes, parent, false);
       return new NoteViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteAdapterClass.NoteViewHolder holder, int position, @NonNull NotesModel model) {

        holder.titenotes.setText(model.title);
        holder.contentNotes.setText(model.content);
        holder.timeoDate_of_notes.setText(Utility.TimeStamp_to_String(model.timestamp));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(view.getContext(), EditNoteActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("title", model.title);
                intent.putExtra("content", model.content);

                String docId = getSnapshots().getSnapshot(holder.getAbsoluteAdapterPosition()).getId();
                intent.putExtra("docId", docId);
                view.getContext().startActivity(intent);
            }
        });


    }



    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView titenotes, contentNotes, timeoDate_of_notes;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titenotes = itemView.findViewById(R.id.title);
            contentNotes = itemView.findViewById(R.id.content);
            timeoDate_of_notes = itemView.findViewById(R.id.Date);

        }
    }
}
