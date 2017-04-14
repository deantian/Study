package com.dean.study.webdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.web_view);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JSHook(), "android");
        mWebView.loadUrl("http://test.mediportal.com.cn/drug/web/dev/content/htmlPages/scoreInstruction/instruction.html");
    }
    public class JSHook{
        @JavascriptInterface
        public void go_score_detail(int d){
            Toast.makeText(MainActivity.this,"haha"+d,Toast.LENGTH_SHORT).show();
        }

    }

}
