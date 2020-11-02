package com.example.lms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.arch.core.internal.FastSafeIterableMap;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.common.ViewFiles;
import com.example.lms.fileupload.UploadFiles;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ViewFilesAdapter extends RecyclerView.Adapter<ViewFilesAdapter.ViewHolder> {

    List<UploadFiles> detail;
    ViewFiles viewFiles;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("HNDIT").child("1st Year 2nd Sem").child("OOP");
    //Context context;
    //private CardView download;

    public ViewFilesAdapter(List<UploadFiles> detail, ViewFiles viewFiles) {
        this.detail = detail;
        this.viewFiles = viewFiles;
    }

    @NonNull
    @Override
    public ViewFilesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_option,parent,false);
        ViewHolder viewHolder = new ViewHolder(view, viewFiles);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewFilesAdapter.ViewHolder holder, final int position) {
        UploadFiles uploadFiles = detail.get(position);
        holder.fileName.setText(detail.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return detail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fileName;
        CheckBox checkBox;
        ImageButton imageButton;
        View view;

        public ViewHolder(@NonNull View itemView,ViewFiles viewFiles) {
            super(itemView);
            fileName = itemView.findViewById(R.id.text_view1);
            //checkBox = itemView.findViewById(R.id.checkbox_del);
            imageButton = itemView.findViewById(R.id.btnRemoveImage1);
            view = itemView;


        }

    }

}

