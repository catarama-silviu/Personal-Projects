package com.example.nfc_transfer_files;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private TextView txtview_path;
    private Button btnSearch;
    private Intent myIntent;
    private Context context;

    Intent myFileIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();



        txtview_path = (TextView) findViewById(R.id.txtPath);
        btnSearch = (Button) findViewById(R.id.btnSearchFile);
        context= getApplicationContext();

        PackageManager pm = this.getPackageManager();
        // Check whether NFC is available on device
        if (!pm.hasSystemFeature(PackageManager.FEATURE_NFC)) {
            // NFC is not available on the device.
            Toast.makeText(this, "The device does not has NFC hardware.",
                    Toast.LENGTH_SHORT).show();
        }
        // Check whether device is running Android 4.1 or higher
        else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            // Android Beam feature is not supported.
            Toast.makeText(this, "Android Beam is not supported.",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            // NFC and Android Beam file transfer is supported.
            Toast.makeText(this, "Android Beam is supported on your device.",
                    Toast.LENGTH_SHORT).show();
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent = new Intent(Intent.ACTION_GET_CONTENT);
                myIntent.setType("*/*");
                startActivityForResult(myIntent,10);
            }
        });
    }

    public void sendFile(View view) throws IOException {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        // Check whether NFC is enabled on device
        if(!nfcAdapter.isEnabled()){
            // NFC is disabled, show the settings UI
            // to enable NFC
            Toast.makeText(this, "Please enable NFC.",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
        }
        // Check whether Android Beam feature is enabled on device
        else if(!nfcAdapter.isNdefPushEnabled()) {
            // Android Beam is disabled, show the settings UI
            // to enable Android Beam
            Toast.makeText(this, "Please enable Android Beam.",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFCSHARING_SETTINGS));
        }
        else {
            // NFC and Android Beam both are enabled

            // File to be transferred
            // For the sake of this tutorial I've placed an image
            // named 'wallpaper.png' in the 'Pictures' directory
            String fileName = "wallpaper.png";

            // Retrieve the path to the user's public pictures directory
           File fileDirectory = Environment
                    .getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES);

            // Create a new file using the specified directory and name
           //File fileToTransfer = new File(fileDirectory, fileName);
            File fileToTransfer= File.createTempFile("test",".test");

            int files1= Integer.parseInt(String.valueOf(fileToTransfer.length()/1024));
            FileOutputStream fos = new FileOutputStream(fileToTransfer);

            InputStream is = context.getContentResolver().openInputStream(Uri.parse(txtview_path.getText().toString()));
            byte[] buffer = new byte[1024];
            int len = 0;
            try {
                len = is.read(buffer);
                while (len != -1) {
                    fos.write(buffer, 0, len);
                    len = is.read(buffer);
                }

                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int files= Integer.parseInt(String.valueOf(fileToTransfer.length()/1024));

            if(fileToTransfer.exists()){
                fileToTransfer.setReadable(true, false);

                nfcAdapter.setBeamPushUris(
                        new Uri[]{Uri.fromFile(fileToTransfer)}, this);

                Toast.makeText(this, "Done.",
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Not find file",
                        Toast.LENGTH_SHORT).show();
            }


        }
    }



    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {

            case 10:
                if (resultCode == RESULT_OK) {
                    String path = data.getData().toString();
                    txtview_path.setText(path);
                }
                break;

        }
    }

    public void checkPermissions() {

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, " De ce esti golan si  dai accept la permisiuni? Vr sa iti fur banii de pe tel?",
                    Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

    }
}