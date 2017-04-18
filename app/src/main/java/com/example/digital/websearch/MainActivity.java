package com.example.digital.websearch;

import android.app.Activity;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    // 定义函数
    public WebView webView;
    public Button btn_go;
    public TextView edt_url;
    public Button btn_save;
    public Context context;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 定义变量
        webView = (WebView) findViewById(R.id.web);
        btn_go = (Button) findViewById(R.id.btn_go);
        edt_url = (TextView) findViewById(R.id.editText);
        btn_save = (Button) findViewById(R.id.save);
        context = getApplicationContext();

        // 基本页面设置
        webView.getSettings().setJavaScriptEnabled(true); // javascript enable
        webView.getSettings().setSupportZoom(true); // zoom enable
        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM); // default far
        webView.getSettings().setBuiltInZoomControls(true); // 缩放工具
        webView.setDrawingCacheEnabled(true);
        webView.loadUrl("http://www.bing.com"); // 载入url
        webView.requestFocus(); // 使页面获得焦点

        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("http://" + edt_url.getText().toString()); //访问编辑框中网址
            }
        });

        // 固定显示在 webview 里
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,String url){
                Toast.makeText(context,"Loading",Toast.LENGTH_SHORT).show();
                view.loadUrl(url);
                return true;
            }
        });

        //进度条
        final Activity activity = this;
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress){
                activity.setTitle("loading");
                if(newProgress == 90){
                    activity.setTitle(R.string.app_name);
                }
            }

        });

        // 退回上一个页面，这样就不会直接退出了
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                        webView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });


 /*      btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                webView.buildDrawingCache();
                Picture pic = webView.capturePicture();
                int width = pic.getWidth();
                int height = pic.getHeight();
                if (width > 0 && height > 0) {
                    Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bmp);
                    pic.draw(canvas);
                    try {
                        String filename = "sdcard/" + System.currentTimeMillis() + ".jpg";
                        FileOutputStream fos = new FileOutputStream(filename);
                        if (fos != null) {
                            bmp.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                            fos.close();
                        }
                        Toast.makeText(context, "截图成功，文件名为：" + filename, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
*/

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page")   // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
