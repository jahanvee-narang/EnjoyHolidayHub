package personal.com.mywebviewnewexample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isNetworkAvailable()){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
            }
            else {
                    snack_barmessage( "No Internet Connection" );
                }
        }
    },3000);

    }

    public void snack_barmessage(String message) {

        Snackbar snackbar = Snackbar.make( findViewById( android.R.id.content ), message, Snackbar.LENGTH_LONG );
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById( android.support.design.R.id.snackbar_text );
        sbView.setBackgroundColor( ContextCompat.getColor( getApplicationContext(), R.color.black ) );
        textView.setTextColor( Color.YELLOW );
        snackbar.show();
    }

    private boolean isNetworkAvailable() {
        @SuppressLint("WrongConstant") ConnectivityManager connectivitymanager = (ConnectivityManager) getApplicationContext().getSystemService( "connectivity" );
        if (connectivitymanager != null) {
            NetworkInfo anetworkinfo[] = connectivitymanager.getAllNetworkInfo();
            if (anetworkinfo != null) {
                for (int i = 0; i < anetworkinfo.length; i++) {
                    if (anetworkinfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }

            }
        }
        return false;
    }
}