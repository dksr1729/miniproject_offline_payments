package com.matrix_maeny.bluetoothchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetSocketAddress;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    private static final int FIND_REQUEST = 3;
    AppCompatButton hostBtn, clientBtn;
    Intent intent = null;
    Intent nintent = null;
    final int UPI_PAYMENT = 0;
    private String amountEdt, upiEdt, nameEdt, descEdt;
    private TextView transactionDetailsTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        amountEdt = "20";
        upiEdt = "dksr1729@okaxis";
        nameEdt = "miniproject";
        descEdt = "buy tokens after payment";


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestFindDevicesPermission();
        }


        hostBtn = findViewById(R.id.hostBtn);
        clientBtn = findViewById(R.id.clientBtn);
        intent = new Intent(MainActivity2.this, MainActivity.class);
        nintent = new Intent(MainActivity2.this, PayPage.class);
    }

    public void HOSTBtn(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestFindDevicesPermission();
        } else {
            intent.putExtra("name", "Host");
            startActivity(intent);
        }
    }

    public void makesPayment(View view) {
        Toast.makeText(MainActivity2.this, "initiating payments", Toast.LENGTH_SHORT).show();
        EditText aamount = findViewById(R.id.aminp);
        String amt = aamount.getText().toString();
        EditText uupi = findViewById(R.id.amupi);
        String upiid = uupi.getText().toString();
        payUsingUpi(amt, upiid, "Mini Project");
    }

    void payUsingUpi(String amount, String upiId, String name) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)                  // YOUR UPI ID
                .appendQueryParameter("pn", name)                  // USE YOUR UPI ID NOT YOUR NAME
//                .appendQueryParameter("mc", "1234")
//                .appendQueryParameter("tid", "123456")
//                .appendQueryParameter("tr", "asgdfuyas3153")
                .appendQueryParameter("am", amount)
                .appendQueryParameter("mam", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this,"Npay,starts",Toast.LENGTH_SHORT).show();
        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        //Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);

                    } else {
                        //Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");

                    }
                } else {
                    //Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");

                }
                break;
        }
    }


    public void CLIENTBtn(View view) {
//        Toast.makeText(MainActivity2.this,"reciever",Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestFindDevicesPermission();
        } else {
            intent.putExtra("name", "Client");
            startActivity(intent);
        }
    }


    final void requestFindDevicesPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity2.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(MainActivity2.this)
                    .setTitle("Permission needed")
                    .setMessage("requires access to location to find devices")
                    .setPositiveButton("ok", (dialog, which) -> ActivityCompat.requestPermissions(MainActivity2.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FIND_REQUEST))
                    .setNegativeButton("cancel", (dialog, which) -> {
                        tempToast();
                        dialog.dismiss();
                    }).create().show();
        } else {

            ActivityCompat.requestPermissions(MainActivity2.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FIND_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == FIND_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "permission granted for finding devices", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied for finding devices", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void tempToast() {
        Toast.makeText(this, "Permission cancelled", Toast.LENGTH_LONG).show();
    } // this is used for toast messages

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(MainActivity2.this, AboutActivity.class));
        return true;
    }
}