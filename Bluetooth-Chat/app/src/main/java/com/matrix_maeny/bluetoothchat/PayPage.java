package com.matrix_maeny.bluetoothchat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PayPage extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Toast.makeText(PayPage.this, "vachesam",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(PayPage.this, AboutActivity.class));
    }
}
