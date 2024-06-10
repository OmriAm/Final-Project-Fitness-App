package com.example.fitnessapp.Screens;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;

public class ExerciseDetailActivity extends AppCompatActivity {
 String url;
    private WebView webView;
    private Dialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);
        url=getIntent().getStringExtra("url");
        /////loading dialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        webView = findViewById(R.id.webview);
        loadingDialog.show();
        // Enable JavaScript (if needed)
        webView.getSettings().setJavaScriptEnabled(true);
        // Set up WebViewClient to manage page loading
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // Show loading dialog when page starts loading

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // Dismiss loading dialog when page finishes loading
                loadingDialog.dismiss();
            }
        });
        // Load a web page
        webView.loadUrl(url);
    }
}