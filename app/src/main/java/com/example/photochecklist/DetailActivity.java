package com.example.photochecklist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photochecklist.models.TaskItem;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    TaskItem item;
    int position;

    private Button btnComplete;
    private TextView tvTitle;
    private TextView tvDescription;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        btnComplete = findViewById(R.id.btnComplete);
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        imageView = findViewById(R.id.itemImageView);

        item = Parcels.unwrap(getIntent().getParcelableExtra(TaskItem.class.getSimpleName()));
        position = getIntent().getIntExtra("position", 0);
        tvTitle.setText(item.title);
        tvDescription.setText(item.description);
        imageView.setImageBitmap(item.image);

        // Set button based on completion status
        if (item.isComplete) {
            btnComplete.setText(R.string.completed);
            btnComplete.setBackgroundColor(ContextCompat.getColor(this, R.color.teal_200));
        }
        // A lambda instead of an anonymous function
        btnComplete.setOnClickListener(v -> toggleCompletionButton());
    }

    private void toggleCompletionButton() {
        item.toggleCompletion();
        if (item.isComplete) {
            btnComplete.setText(R.string.completed);
            btnComplete.setBackgroundColor(ContextCompat.getColor(this, R.color.teal_200));
        } else {
            btnComplete.setText(R.string.mark_complete);
            btnComplete.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_200));
        }

        // Pass updated values back to Main
        Intent data = new Intent();
        data.putExtra("position", position);
        data.putExtra("updated_item", Parcels.wrap(item));
        setResult(Activity.RESULT_OK, data);
    }
}