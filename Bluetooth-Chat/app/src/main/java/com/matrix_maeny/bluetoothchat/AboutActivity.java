package com.matrix_maeny.bluetoothchat;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class AboutActivity extends Activity {

    TextView textmsg;
    static final int READ_BLOCK_SIZE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        textmsg = (TextView) findViewById(R.id.textView7);
        String textdata =textmsg.getText().toString();
        ReadBtn(textmsg);
        }

    // write text to file
    public void WriteBtn(String v) {
        // add-write text into file
        try {
            FileOutputStream fileout=openFileOutput("mytextfile.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(v);
            outputWriter.close();

            //display file saved message
            Toast.makeText(getBaseContext(), "Updated wallet successfully!",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        textmsg.setText(v.toString());
    }

    // Read text from file
    public void ReadBtn(View v)
    {

        File f = new File("mytextfile.txt");
        if(f.exists() && !f.isDirectory()) {
            int temp_vars_a = 0;
        }
        else {
            try {
                if(f.createNewFile()){
                    try {
                        FileOutputStream fileout = openFileOutput("mytextfile.txt", MODE_PRIVATE);
                        OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                        outputWriter.write("5578");
                        outputWriter.close();
                    }
                    catch(Exception e){
                        int temp_vars_b = 0;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //reading text from file
        try {

            File ff = new File("mytextfile.txt");
            if(ff.exists() && !ff.isDirectory()) {
                int temp_vars_a = 0;
            }
            else{
                WriteBtn("5577");
            }


            FileInputStream fileIn=openFileInput("mytextfile.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);
            char[] inputBuffer= new char[READ_BLOCK_SIZE];
            String s="";
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();
            textmsg.setText(s);
            WriteBtn(s);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "unable to fetch balance" ,
                    Toast.LENGTH_SHORT).show();
        }

    }
}