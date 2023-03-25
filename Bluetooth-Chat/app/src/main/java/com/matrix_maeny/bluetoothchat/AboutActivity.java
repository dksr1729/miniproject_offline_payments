package com.matrix_maeny.bluetoothchat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

public class AboutActivity extends Activity {

    TextView textmsg;

    static final int READ_BLOCK_SIZE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        textmsg = (TextView)findViewById(R.id.textView7);
        ReadBtn();
        }

    // Read text from file
    public void ReadBtn() {
        //reading text from file
        try {

            SharedPreferences pref = getSharedPreferences("MyPref", 0); // 0 - for private mode
            String amount = pref.getString("amount","");
            if(amount == "" || amount == null){
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("amount","0000");
                editor.apply();
            }
            textmsg.setText(amount);

            //display balance fetched message
            Toast.makeText(getBaseContext(), "Read Balance successfully!" + amount,
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            //display file saved message
            Toast.makeText(getBaseContext(), "file :"+e,
                    Toast.LENGTH_SHORT).show();
        }
    }
}