package com.example.vamsi.fingerprintauth;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mHeadingLabel;
    private ImageView mFingerprintImage;
    private TextView mParaLabel;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHeadingLabel = findViewById(R.id.headingLabel);
        mFingerprintImage= findViewById(R.id.fingerprintImage);
        mParaLabel = findViewById(R.id.paraLabel);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);


            if(!fingerprintManager.isHardwareDetected()) {

               mParaLabel.setText("Fingerprint scanner not detected in device");

            }else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){

                mParaLabel.setText("Permission not granted for using Fingerprint scanner");

            }else if(!keyguardManager.isDeviceSecure()){

                mParaLabel.setText("Add lock to your phone in settings");

            }else if(!fingerprintManager.hasEnrolledFingerprints()){

                mParaLabel.setText("You should add atleast 1 fingerprint to use this feature");

            }else{

                mParaLabel.setText("Place your finger on scanner to access the App");
                FingerprintHandler fingerprintHandler = new FingerprintHandler(this);
                fingerprintHandler.startAuth(fingerprintManager,null);

            }

        }

    }
}
