package com.example.lms.adminPanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.R;
import com.example.lms.admin.adminactivity.AdminProfile;

import java.util.List;

public class PanelRecyclerView {
    private Context mContext;
    private AdminAdapter mAdminAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<AdminProfile> profile, List<String> keys){
        mContext = context;
        mAdminAdapter = new AdminAdapter(profile, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mAdminAdapter);
    }


    class AdminPanelView extends RecyclerView.ViewHolder {
        private TextView Name;
        private TextView Dob;
        private TextView ID;
        private TextView Email;

        private String key;

        public AdminPanelView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.admin_panel_list,parent,false));

            Name = (TextView) itemView.findViewById(R.id.tvName);
            Dob = (TextView) itemView.findViewById(R.id.tvDob);
            ID = (TextView) itemView.findViewById(R.id.tvID);
            Email = (TextView) itemView.findViewById(R.id.tvEmail);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AdminDetails.class);
                    intent.putExtra("key", key);
                    intent.putExtra("adminName", Name.getText().toString());
                    intent.putExtra("adminEmail", Email.getText().toString());
                    intent.putExtra("adminIdNumber", ID.getText().toString());
                    intent.putExtra("adminDOB", Dob.getText().toString());

                    mContext.startActivity(intent);
                }
            });

        }

        public void bind(AdminProfile adminProfile, String key){

            Name.setText(adminProfile.getAdminName());
            Email.setText(adminProfile.getAdminEmail());
            ID.setText(adminProfile.getAdminIdNumber());
            Dob.setText(adminProfile.getAdminDOB());
            this.key=key;

        }
    }

    class AdminAdapter extends RecyclerView.Adapter<AdminPanelView>{
        private List<AdminProfile> profile;
        private List<String> keys;

        public AdminAdapter(List<AdminProfile> profile, List<String> keys) {
            this.profile = profile;
            this.keys = keys;
        }

        @NonNull
        @Override
        public AdminPanelView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdminPanelView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull AdminPanelView holder, int position) {
            holder.bind(profile.get(position),keys.get(position));
        }

        @Override
        public int getItemCount() {
            return profile.size();
        }
    }

}
