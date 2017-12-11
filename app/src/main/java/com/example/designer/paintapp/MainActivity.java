package com.example.designer.paintapp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    String url = "http://vpolly.com/paint/";
    boolean loadingFinished = true;
    boolean redirect = false;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            WebViewClient yourWebClient = new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    view.loadUrl(url);
                }
            };
            webView = (WebView) findViewById(R.id.webview);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
//        webView.setWebViewClient( new WebViewClient());
//        webView1.setWebViewClient(yourWebClient,url);
            webView.loadUrl(url);
            webView.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");

    /*    webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        httpURLConnection =  HttpURLConnection(url);
        httpURLConnection.setConnectTimeout(7000);*/

            webView.setWebViewClient(new WebViewClient(){

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean shouldOverrideUrlLoading(
                        WebView view, WebResourceRequest request) {
                    if (!loadingFinished) {
                        redirect = true;
                    }

                    loadingFinished = false;
                    webView.loadUrl(request.getUrl().toString());
                    return true;
                }

                @Override
                public void onPageStarted(
                        WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    loadingFinished = false;
                    //SHOW LOADING IF IT ISNT ALREADY VISIBLE
                    pd = new ProgressDialog(MainActivity.this);
                    pd.setMessage("loading");
                    pd.show();
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    if(!redirect){
                        loadingFinished = true;
                    }

                    if(loadingFinished && !redirect){
                        pd.dismiss();
                        //HIDE LOADING IT HAS FINISHED
                    } else{
                        redirect = false;
                    }
                }
            });

       /* OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES);

        okHttpClient = builder.build();*/

        }
    }
