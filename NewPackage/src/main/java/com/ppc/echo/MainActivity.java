package com.ppc.echo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AppCompatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: app is starting ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
