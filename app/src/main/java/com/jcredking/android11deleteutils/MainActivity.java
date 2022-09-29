package com.jcredking.android11deleteutils;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.jcredking.deleteutilsr.DeleteUtilsR;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
        DeleteUtilsR deleteUtilsR = new DeleteUtilsR(this);
        copyAssets();
        File file = new File(externalStoragePublicDirectory, getString(R.string.app_name));
        File[] files = file.listFiles();
        Log.d("Files", "Size: "+ files.length);
        ArrayList<File> arrayList = new ArrayList<>(Arrays.asList(files));
        RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setAdapter( new Adepter(arrayList, this,deleteUtilsR));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        for (int i = 0; i < files.length; i++)
        {
            Log.d("dfdffddf", "FileName:" + files[i].getName());
        }

    }

    private void copyAssets() {
        AssetManager assetManager = getAssets();
          File file = new File(externalStoragePublicDirectory, getString(R.string.app_name));
        if (!file.exists()) {
            file.mkdirs();
        }
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        for(String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);

                String outDir = Environment.DIRECTORY_DOWNLOADS + getString(R.string.app_name) ;
                File outFile = new File(file, filename);

                out = new FileOutputStream(outFile);
                copyFile(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}