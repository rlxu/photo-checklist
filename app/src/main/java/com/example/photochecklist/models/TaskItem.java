package com.example.photochecklist.models;

import android.graphics.Bitmap;

import org.parceler.Parcel;

@Parcel
public class TaskItem {

    // list of attributes
    public boolean isComplete;
    public String title;
    public String description;
    public Bitmap image;

    public TaskItem() {
        // all items are initially incomplete
        this.isComplete = false;
    }

    public TaskItem(String title, String description) {
        this.isComplete = false;
        this.title = title;
        this.description = description;
    }

    public void toggleCompletion() {
        this.isComplete = !this.isComplete;
    }
}
