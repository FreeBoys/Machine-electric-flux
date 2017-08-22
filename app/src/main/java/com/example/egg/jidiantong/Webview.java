package com.example.egg.jidiantong;

/**
 * Created by egg on 17-8-21.
 */




import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Webview extends Activity {
    private WebView web1;
    //private String url="http://scemi.com/";
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取Intent传来的URL
        Intent intent = getIntent();
        url = intent.getStringExtra("URL_String");


        setContentView(R.layout.webview);
        web1 = (WebView)findViewById(R.id.we1);
        web1.loadUrl(url);

        web1.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.i("TAG",url);
                return super.shouldOverrideUrlLoading(view, request);

            }
        });

        //web1.getSettings().getCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        web1.getSettings().setJavaScriptEnabled(true );


//        web1.requestFocus();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            if(web1.canGoBack())
            {
                web1.goBack();
                return true;
            }
            else
            {
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

