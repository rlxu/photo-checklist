package com.example.photochecklist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.photochecklist.models.TaskItem;

import org.parceler.Parcels;

public class AddActivity extends AppCompatActivity {

    static final int CAMERA_REQUEST_ID = 42;

    private EditText etTitle;
    private EditText etDescription;
    private Button btnAddImage;
    private ImageView addedImageView;
    private Button btnAddItem;

    private Bitmap addedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        btnAddImage = findViewById(R.id.btnAddImage);
        btnAddImage.setOnClickListener(v -> {
            Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePhotoIntent, CAMERA_REQUEST_ID);
        });
        addedImageView = findViewById(R.id.addedImageView);
        addedImageView.setImageBitmap(null); // set the image to nothing initially
        btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(v -> onAddItem(v));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_ID && resultCode == RESULT_OK) {
            // set image preview
            addedImage = (Bitmap) data.getExtras().get("data");
            addedImageView.setImageBitmap(addedImage);
        }
    }

    public void onAddItem(View v) {
        // Check that the title is not blank
        if (etTitle.getText().toString().matches("")) {
            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new TaskItem object
        TaskItem newItem = new TaskItem(etTitle.getText().toString(), etDescription.getText().toString());
        newItem.image = addedImage;

        // Go back to MainActivity with new item
        Intent data = new Intent();
        data.putExtra("task_item", Parcels.wrap(newItem));
        setResult(RESULT_OK, data);
        finish();
    }
}
