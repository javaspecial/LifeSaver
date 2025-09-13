package com.peoples.shield.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.peoples.shield.R;
import com.peoples.shield.handler.RegisterEntity;
import com.peoples.shield.room.AppDatabase;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        RegisterEntity.doRegisterEntity(AppDatabase.getInstance(this));

        Button btnConnect = findViewById(R.id.btn_connect);
        btnConnect.setOnClickListener(v -> {
            btnConnect.setText("CONNECTING");
            btnConnect.setEnabled(false);

            // Create a small indeterminate progress spinner
            ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleSmallTitle);
            progressBar.setIndeterminate(true);

            // Force size (30x30dp looks good for a button icon)
            int size = (int) (30 * getResources().getDisplayMetrics().density);
            progressBar.setLayoutParams(new ViewGroup.LayoutParams(size, size));

            // Get the drawable of the ProgressBar (the spinning circle)
            Drawable spinner = progressBar.getIndeterminateDrawable();
            btnConnect.setCompoundDrawablesRelativeWithIntrinsicBounds(spinner, null, null, null);

            // After 2 sec, remove spinner + go to MainActivity
            new Handler().postDelayed(() -> {
                btnConnect.setText("CONNECTED");
                btnConnect.setCompoundDrawablesRelative(null, null, null, null);
                startActivity(new Intent(LandingActivity.this, MainActivity.class));
                finish();
            }, 2000);
        });
    }
}
