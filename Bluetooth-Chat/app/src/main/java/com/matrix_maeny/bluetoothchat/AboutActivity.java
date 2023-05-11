package com.matrix_maeny.bluetoothchat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.webkit.JavascriptInterface;
import android.content.ClipboardManager;
import android.content.ClipData;

public class AboutActivity extends Activity {

    TextView textmsg;
    WebView mywebview;
    ClipboardManager myClipboard;
    ClipData myClip;
    static final int READ_BLOCK_SIZE = 100;
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        textmsg = (TextView)findViewById(R.id.textView7);
        ReadBtn();
        mywebview = findViewById(R.id.webView);
        mywebview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mywebview.loadUrl("http://13.50.110.233:8000/");
        // this will enable the javascript.
        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.getSettings().setSupportZoom(true);
        mywebview.addJavascriptInterface(this, "Dialog");
        SharedPreferences pref = getSharedPreferences("MyPref", 0); // 0 - for private mode
        String text = pref.getString("transactions","");
        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        myClip = ClipData.newPlainText("text", text);
        myClipboard.setPrimaryClip(myClip);
        mywebview.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url){
                SharedPreferences pref = getSharedPreferences("MyPref", 0); // 0 - for private mode
                String trnsc = pref.getString("transactions","");
                mywebview.loadUrl("javascript:init('" + trnsc+ "')");
            }

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

    @JavascriptInterface
    public void showMsg(String fname, String pswd) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
        builder.setTitle("Confirmation").setMessage("UserName:\t" + fname +
                        "\nPassword:\t" + pswd + "successful")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), " Data Saved Locally", Toast.LENGTH_SHORT).show();
                        // You can use shared preference or db here to store The Data
                    }
                })
        ;

        builder.create().show();
    }

    @JavascriptInterface
    public void addtowallet(String amounts) {
        SharedPreferences pref = getSharedPreferences("MyPref", 0); // 0 - for private mode
        String amount = pref.getString("amount","");
        int cb = Integer.parseInt(amount);
        cb = cb + Integer.parseInt(amounts);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("amount",String.valueOf(cb));
        editor.apply();

        Toast.makeText(getBaseContext(), "added "+amounts+" to wallet...", Toast.LENGTH_SHORT).show();
        ReadBtn();
    }

    @JavascriptInterface
    public void updatethetrnscns(){
        Toast.makeText(getApplicationContext(), "fetching app data", Toast.LENGTH_SHORT).show();
        mywebview.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url){
                SharedPreferences pref = getSharedPreferences("MyPref", 0); // 0 - for private mode
                String trnsc = pref.getString("transactions","");
                mywebview.loadUrl("javascript:transactionsfromapp('" + trnsc+ "')");
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });
    }

    // Read text from file
    public void ReadBtn() {
        //reading text from file and wallet initialization
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