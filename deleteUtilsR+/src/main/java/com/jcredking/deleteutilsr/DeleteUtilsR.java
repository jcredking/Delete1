package com.jcredking.deleteutilsr;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class DeleteUtilsR {
    AppCompatActivity activity;
    DeleteCallBack deleteCallBack;
    ActivityResultLauncher<IntentSenderRequest> launcher;

    public DeleteUtilsR(AppCompatActivity activity) {
        this.activity = activity;
        launcher();
    }


    public void deleteAudio(String path, DeleteCallBack deleteCallBack) {
        this.deleteCallBack = deleteCallBack;
        ArrayList<Uri> arrayList = new ArrayList<>(Arrays.asList(ContentUri(activity, path, "Audio")));
        if (Build.VERSION.SDK_INT >= 30) {
            delete1Array(arrayList);
        } else {
            if (delete0Array(new ArrayList<>(Arrays.asList(path)))) {
                deleteCallBack.onDeleted();
            }
        }

    }

    public void deleteAudiosList(ArrayList<String> paths, DeleteCallBack deleteCallBack) {
        ArrayList<Uri> stringArrayList = new ArrayList<>();
        for (int i = 0; i < paths.size(); i++) {
            stringArrayList.add(ContentUri(activity, paths.get(i), "Audio"));
        }
        if (Build.VERSION.SDK_INT >= 30) {
            delete1Array(stringArrayList);
        } else {
            if (delete0Array(paths)) {
                deleteCallBack.onDeleted();
            }
        }
    }

    public void deleteImage(String path, DeleteCallBack deleteCallBack) {
        this.deleteCallBack = deleteCallBack;
        ArrayList<Uri> arrayList = new ArrayList<>(Arrays.asList(ContentUri(activity, path, "Image")));
        if (Build.VERSION.SDK_INT >= 30) {
            delete1Array(arrayList);
        } else {
            if (delete0Array(new ArrayList<>(Arrays.asList(path)))) {
                deleteCallBack.onDeleted();
            }
        }
    }

    public void deleteImagesList(ArrayList<String> paths, DeleteCallBack deleteCallBack) {
        ArrayList<Uri> stringArrayList = new ArrayList<>();
        for (int i = 0; i < paths.size(); i++) {
            stringArrayList.add(ContentUri(activity, paths.get(i), "Image"));
        }
        if (Build.VERSION.SDK_INT >= 30) {
            delete1Array(stringArrayList);
        } else {
            if (delete0Array(paths)) {
                deleteCallBack.onDeleted();
            }
        }
    }

    public void deleteVideo(String path, DeleteCallBack deleteCallBack) {
        this.deleteCallBack = deleteCallBack;
        ArrayList<Uri> arrayList = new ArrayList<>(Arrays.asList(ContentUri(activity, path, "Video")));
        if (Build.VERSION.SDK_INT >= 30) {
            delete1Array(arrayList);
        } else {
            if (delete0Array(new ArrayList<>(Arrays.asList(path)))) {
                deleteCallBack.onDeleted();
            }
        }
    }

    public void deleteVideosList(ArrayList<String> paths, DeleteCallBack deleteCallBack) {
        ArrayList<Uri> stringArrayList = new ArrayList<>();
        for (int i = 0; i < paths.size(); i++) {
            stringArrayList.add(ContentUri(activity, paths.get(i), "Video"));
        }
        if (Build.VERSION.SDK_INT >= 30) {
            delete1Array(stringArrayList);
        } else {
            if (delete0Array(paths)) {
                deleteCallBack.onDeleted();
            }
        }
        delete1Array(stringArrayList);
    }


    @SuppressLint("Range")
    private Uri ContentUri(Context context, String path, String type) {
        File file = new File(path);
        String filePath = file.getAbsolutePath();
        Uri uri;
        Uri mediaStore = null;
        String contentUri = null;
        switch (type) {
            case "Audio":
                mediaStore = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                contentUri = "content://media/external/audio/media";
                break;
            case "Image":
                mediaStore = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                contentUri = "content://media/external/images/media";
                break;
            case "Video":
                mediaStore = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                contentUri = "content://media/external/video/media";
                break;
        }
        Cursor cursor = context.getContentResolver().query(mediaStore, new String[]{"_id"}, "_data=? ", new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            return Uri.withAppendedPath(Uri.parse(contentUri), "" + cursor.getInt(cursor.getColumnIndex("_id")));
        } else if (!file.exists()) {
            return null;
        } else {
            ContentValues values = new ContentValues();
            values.put("_data", filePath);
            return context.getContentResolver().insert(mediaStore, values);
        }
    }

    private void delete1Array(ArrayList<Uri> uri) {
        ContentResolver contentResolver = activity.getApplicationContext().getContentResolver();
        PendingIntent pendingIntent = null;
        if (Build.VERSION.SDK_INT >= 30) {
            pendingIntent = MediaStore.createDeleteRequest(contentResolver, uri);
        }
        if (pendingIntent != null) {
            PendingIntent finalPendingIntent = pendingIntent;
            launcher.launch(new IntentSenderRequest.Builder(pendingIntent.getIntentSender()).build());
        }
    }

    private boolean delete0Array(ArrayList<String> paths) {
        for (int i = 0; i < paths.size(); i++) {
            try {
                File file = new File(paths.get(i));
                if (file.exists()) {
                    file.delete();
                }
                return true;
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return false;
    }


    private void launcher() {
        launcher = activity.registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), new ActivityResultCallback() {
            @Override
            public final void onActivityResult(Object obj) {
                ActivityResult result = (ActivityResult) obj;
                if (result.getResultCode() == -1) {
                    deleteCallBack.onDeleted();
                }

            }
        });
    }


}
