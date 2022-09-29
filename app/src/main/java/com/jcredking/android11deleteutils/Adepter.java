package com.jcredking.android11deleteutils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jcredking.deleteutilsr.DeleteCallBack;
import com.jcredking.deleteutilsr.DeleteUtilsR;

import java.io.File;
import java.util.ArrayList;

public class Adepter extends RecyclerView.Adapter<Adepter.MyViewHolder> {

    ArrayList<File> files;
    Activity activity;
    DeleteUtilsR deleteUtilsR;

    public Adepter(ArrayList<File> files, Activity activity, DeleteUtilsR deleteUtilsR) {
        this.files = files;
        this.activity = activity;
        this.deleteUtilsR = deleteUtilsR;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.fileName.setText(files.get(position).getName());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (files.get(position).getPath().contains(".mp3")) {
                    deleteUtilsR.deleteAudio(files.get(position).getPath(), new DeleteCallBack() {
                        @Override
                        public void onDeleted() {
                            Toast.makeText(activity, "Deleted", Toast.LENGTH_SHORT).show();
                            files.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                }
                if (files.get(position).getPath().contains(".png")) {
                    deleteUtilsR.deleteImage(files.get(position).getPath(), new DeleteCallBack() {
                        @Override
                        public void onDeleted() {
                            Toast.makeText(activity, "Deleted", Toast.LENGTH_SHORT).show();
                            files.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                }
                if (files.get(position).getPath().contains(".mp4")) {
                    deleteUtilsR.deleteVideo(files.get(position).getPath(), new DeleteCallBack() {
                        @Override
                        public void onDeleted() {
                            Toast.makeText(activity, "Deleted", Toast.LENGTH_SHORT).show();
                            files.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fileName;
        ImageView btnDelete, icon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.fileName);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            icon = itemView.findViewById(R.id.icon);
        }
    }
}
