package com.peoples.shield.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.peoples.shield.R;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Button btnConnect = findViewById(R.id.btn_connect);
        btnConnect.setOnClickListener(v -> {
            btnConnect.setText("Connecting...");
            btnConnect.setEnabled(false);

            Drawable progressDrawable = getResources().getDrawable(R.drawable.ic_spinner);
            progressDrawable.setBounds(0, 0, 60, 60);
            btnConnect.setCompoundDrawablesRelative(progressDrawable, null, null, null);

            new Handler().postDelayed(() -> {
                btnConnect.setCompoundDrawablesRelative(null, null, null, null);
                startActivity(new Intent(LandingActivity.this, MainActivity.class));
                finish();
            }, 100);
        });
    }
}
