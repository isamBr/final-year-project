package com.example.sniketn_pc.diabeticmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class DiabetesNews extends AppCompatActivity {
    private WebView mWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetes_news);
        mWebView =(WebView) findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("https://www.diabetes.ie/");
    }
    public void onMenu(View view)
    {
        //return menu activity
        Intent display = new Intent(getBaseContext(),MenuOption.class);
        startActivity(display);
        finish();
    }
}
