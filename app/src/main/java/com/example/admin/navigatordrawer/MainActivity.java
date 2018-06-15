package com.example.admin.navigatordrawer;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.admin.navigatordrawer.Adapters.MyRecyclerViewAdapter;
import com.example.admin.navigatordrawer.BroadcastRecivers.MyCustomBradcastReciver;
import com.example.admin.navigatordrawer.Security.RSA;
import com.example.admin.navigatordrawer.Services.MyIntentService;
import com.example.admin.navigatordrawer.Services.MyService;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class MainActivity extends AppCompatActivity {
    private MyCustomBradcastReciver receiver;
    private IntentFilter intentFilter;
    private BroadcastReceiver otherReceiver;

    private static final String TAG = "MainActivity";
    private AdView mAdView;

    private BroadcastReceiver localReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("App", "Local Receiver");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-8655174172897393/4726801040");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        receiver= new MyCustomBradcastReciver();
        intentFilter= new IntentFilter("com.code3e.MY_CUSTOM_ACTION");

        registerReceiver(receiver, intentFilter);

        otherReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int value= intent.getIntExtra("value", 0);
                Log.d("App", "Message Received Activity");

                
            }
        };

        registerReceiver(otherReceiver, intentFilter);

        LocalBroadcastManager.getInstance(this).registerReceiver(localReceiver, new IntentFilter("com.code3e.MY_LOCAL_CUSTOM_ACTION"));

        RecyclerView recyclerView= findViewById(R.id.recyclerView);
        ArrayList<String> dataSource= new ArrayList<>();
        for (int i = 0; i < 10; i++){
            dataSource.add("Val: " + i);
        }

        MyRecyclerViewAdapter adapter= new MyRecyclerViewAdapter(this, dataSource);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        adapter.setOnClickListener(new MyRecyclerViewAdapter.MyRecyclerListener() {
            @Override
            public void OnClick(View view, int position) {
                Log.d("App", "position: " + position);

                switch (position){
                    case 0: {
                        drawer.closeDrawer(GravityCompat.START);
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.relativeLayout, new firstFragment());
                        fragmentTransaction.commit();
                    }
                        break;
                    case 1:{
                        drawer.closeDrawer(GravityCompat.START);
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.relativeLayout, new secondFragment());
                        fragmentTransaction.commit();
                    }
                        break;
                    case 2:{
                        drawer.closeDrawer(GravityCompat.START);
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.relativeLayout, new thirdFragment());
                        fragmentTransaction.commit();
                    }
                        break;
                    case 3:{
                        drawer.closeDrawer(GravityCompat.START);
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.relativeLayout, new fourthFragment());
                        fragmentTransaction.commit();
                    }
                        break;
                    case 4:{
                        drawer.closeDrawer(GravityCompat.START);
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.relativeLayout, new fithFragment());
                        fragmentTransaction.commit();
                    }
                    break;
                    case 5:{
                        drawer.closeDrawer(GravityCompat.START);
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.relativeLayout, new SixthFragment());
                        fragmentTransaction.commit();
                    }
                    break;
                        default:
                            break;
                }
            }
        });

        Intent intent= new Intent(this, MyService.class);
        startService(intent);
        Log.d("App", "Start Service");

        ActivityManager manager= (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE)){
            if (serviceInfo.service.getClassName().equals(MyService.class.getName())){
                Log.d("App", "el servicio esta activo");
            }
        }
        requestLocation();

        Intent intent1= new Intent(this, MyIntentService.class);
        intent1.putExtra("value", 100);
        startService(intent1);

        String password= "abc123";

        String md5= getHash(password, "MD5");
        String sha1= getHash(password, "sha1");
        String sha256= getHash(password, "sha256");
        String sha512= getHash(password, "sha512");

        Log.d("App", "Plain: " + password);
        Log.d("App", "MD5: " + md5);
        Log.d("App", "sha1: " + sha1);
        Log.d("App", "sha256: " + sha256);
        Log.d("App", "sha512: " + sha512);

        try{
            String encrypted= encrytText(password);
            String decrypted= decryptText(encrypted);

            Log.d("App", "Encrypted: " + encrypted);
            Log.d("App", "Decrypted: " + decrypted);

            RSA rsa= new RSA();
            String rsaEncrypted= rsa.Encrypt(password);
            String rsaDecrypted= rsa.Decrypt(password);

            Log.d("App", "rsaEncrypted: " + rsaEncrypted);
            Log.d("App", "rsaDecrypted: " + rsaDecrypted);

        }catch (Exception e){

        }

        FirebaseAnalytics myFirebaseAnalytics= FirebaseAnalytics.getInstance(this);

        Bundle bundle= new Bundle();
        bundle.putString(FirebaseAnalytics.Param.PRICE, "200");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Drone");
        myFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, bundle);
    }

    String KEY = "12345678901234567890123456789012";
    String IV_VECTOR = "1234567890123456";

    public String encrytText(String text) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        byte[] static_key = KEY.getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(static_key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV_VECTOR.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] results = cipher.doFinal(text.getBytes());
        String result = Base64.encodeToString(results, Base64.NO_WRAP|Base64.DEFAULT);
        return result;
    }

    public String decryptText(String text)throws Exception{
        byte[] encryted_bytes = Base64.decode(text, Base64.DEFAULT);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        byte[] static_key = KEY.getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(static_key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV_VECTOR.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decrypted = cipher.doFinal(encryted_bytes);
        String result = new String(decrypted);
        return result;
    }

    private String getHash(String plainText, String alg){
        try{
            MessageDigest digest= MessageDigest.getInstance(alg);
            digest.update(plainText.getBytes());
            byte[] encryptedBytes= digest.digest();
            StringBuilder builder= new StringBuilder();
            for(byte mbyte : encryptedBytes){
                String hexString= Integer.toHexString(0XFF & mbyte);
                while(hexString.length() < 2){
                    hexString = "0" + hexString;
                }
                builder.append(hexString);
            }
            return builder.toString();
        }catch(Exception e){
            return "";
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            stopService(new Intent(this, MyService.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(otherReceiver);
    }

    private void requestLocation(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Intent intent= new Intent(this, MyService.class);
            startService(intent);
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2018);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        requestLocation();
    }
}
