package com.example.auth_and_note_app;

import com.google.firebase.Timestamp;

public class NotesModel {
    String title;
    String content;
    com.google.firebase.Timestamp timestamp;

    public NotesModel() {
    }

    public NotesModel(String title, String content, Timestamp timestamp) {
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
