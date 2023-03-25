package com.matrix_maeny.bluetoothchat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

        WebView mywebview = (WebView) findViewById(R.id.webView);
        mywebview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mywebview.loadUrl("https://www.sastra.edu/");
        // this will enable the javascript.
        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }

        });
        mywebview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    WebView webView = (WebView) v;

                    switch(keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView.canGoBack()) {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }

                return false;
            }
        });

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

    public void WebLoading(){
        WebView mywebview = (WebView) findViewById(R.id.webView);
        mywebview.loadUrl("https://www.sastra.edu/");
        // this will enable the javascript.
        mywebview.getSettings().setJavaScriptEnabled(true);

    }
}