package com.example.admin.navigatordrawer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class scannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        scannerView= new ZXingScannerView(this);
        scannerView.setAutoFocus(true);
        setContentView(scannerView);

        checkCamera();
    }

    private void checkCamera(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){

        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 2018);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.startCamera();
        scannerView.setResultHandler(this);
    }


    @Override
    public void handleResult(Result result) {
        String code= result.getText();

        Log.d("App", "Code: " + code);
    }
}
