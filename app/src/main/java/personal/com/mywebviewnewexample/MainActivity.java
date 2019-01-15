package personal.com.mywebviewnewexample;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressbar;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        progressbar = findViewById( R.id.progress_bar );
         webView =    findViewById( R.id.Web_view );
        webView.setWebViewClient(new myWebClient());
        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl( "https://enjoyholidayhub.com/" );
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                progressbar.setVisibility( View.GONE );
            }

            public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
                progressbar.setVisibility( View.GONE );
                try {
                    webView.stopLoading();
                } catch (Exception e) {
                }

                if (webView.canGoBack()) {
                    webView.goBack();
                }

                webView.loadUrl("about:blank");
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this,R.style.AlertDialogCustom).create();
                alertDialog.setTitle("Error");
                String message=("Check your internet connection and try again.");
                alertDialog.setCancelable(false);
                ForegroundColorSpan foregroundColorSpan= new ForegroundColorSpan( Color.BLACK );
                SpannableStringBuilder ssBuilder=new SpannableStringBuilder( message );
                ssBuilder.setSpan( foregroundColorSpan,0,message.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
                alertDialog.setMessage(ssBuilder);

                alertDialog.setButton( DialogInterface.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startActivity(getIntent());
                    }
                });
                alertDialog.show();
               alertDialog.getButton( AlertDialog.BUTTON_POSITIVE ).setTextColor(Color.RED);
                super.onReceivedError(webView, errorCode, description, failingUrl);
            }
        });
    }

    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
