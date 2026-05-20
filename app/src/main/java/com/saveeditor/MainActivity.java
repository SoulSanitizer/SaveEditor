package com.saveeditor;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import java.io.OutputStream;

public class MainActivity extends Activity {

    private WebView webView;
    private ValueCallback<Uri[]> filePathCallback;
    private String pendingSaveContent = null;

    private static final int FILE_CHOOSER_REQUEST  = 1;
    private static final int CREATE_FILE_REQUEST   = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView = new WebView(this);
        setContentView(webView);

        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setAllowFileAccess(true);
        s.setDomStorageEnabled(true);
        s.setAllowFileAccessFromFileURLs(true);
        s.setAllowUniversalAccessFromFileURLs(true);

        // Inject Android save interface into JS
        webView.addJavascriptInterface(new SaveInterface(), "AndroidSave");

        webView.setWebViewClient(new WebViewClient());

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView wv,
                                             ValueCallback<Uri[]> callback,
                                             FileChooserParams params) {
                // Cancel any previous callback
                if (filePathCallback != null) {
                    filePathCallback.onReceiveValue(null);
                }
                filePathCallback = callback;
                Intent intent = params.createIntent();
                try {
                    startActivityForResult(intent, FILE_CHOOSER_REQUEST);
                } catch (Exception e) {
                    filePathCallback = null;
                    Toast.makeText(MainActivity.this,
                        "Cannot open file picker", Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }
        });

        webView.loadUrl("file:///android_asset/editor.html");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Handle file open picker
        if (requestCode == FILE_CHOOSER_REQUEST) {
            if (filePathCallback != null) {
                Uri[] results = null;
                if (resultCode == RESULT_OK && data != null) {
                    results = new Uri[]{ data.getData() };
                }
                filePathCallback.onReceiveValue(results);
                filePathCallback = null;
            }
            return;
        }

        // Handle file save picker (ACTION_CREATE_DOCUMENT)
        if (requestCode == CREATE_FILE_REQUEST) {
            if (resultCode == RESULT_OK && data != null && pendingSaveContent != null) {
                Uri uri = data.getData();
                try {
                    OutputStream os = getContentResolver().openOutputStream(uri);
                    if (os != null) {
                        os.write(pendingSaveContent.getBytes("UTF-8"));
                        os.close();
                        Toast.makeText(this, "✅ File saved!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Save failed: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
                }
            }
            pendingSaveContent = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    // JavaScript interface — called from the HTML editor
    class SaveInterface {
        @JavascriptInterface
        public void saveFile(final String filename, final String content) {
            // Store content and open system file-save dialog on UI thread
            pendingSaveContent = content;
            runOnUiThread(() -> {
                Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/octet-stream");
                intent.putExtra(Intent.EXTRA_TITLE, filename);
                try {
                    startActivityForResult(intent, CREATE_FILE_REQUEST);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this,
                        "Cannot open save dialog: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
                    pendingSaveContent = null;
                }
            });
        }
    }
}
