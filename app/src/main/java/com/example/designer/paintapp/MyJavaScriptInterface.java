package com.example.designer.paintapp;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class MyJavaScriptInterface {

    private Context ctx;
    MyJavaScriptInterface(Context ctx) {
        this.ctx = ctx;
    }

    @JavascriptInterface
    public void showHTML(String html) {
        System.out.println(html);
    }

}
