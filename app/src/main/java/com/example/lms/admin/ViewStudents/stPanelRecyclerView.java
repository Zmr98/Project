package com.example.lms.admin.ViewStudents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.R;
import com.example.lms.user.UserProfile;

import java.util.List;

public class stPanelRecyclerView {

    private Context mContext;
    private stPanelRecyclerView.StuAdapter mStuAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<UserProfile> profile, List<String> keys){
        mContext = context;
        mStuAdapter = new stPanelRecyclerView.StuAdapter(profile, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mStuAdapter);
    }


    class StudentListView extends RecyclerView.ViewHolder {
        private TextView Name;
        private TextView Dob;
        private TextView ID;
        private TextView Email;

        private String key;

        public StudentListView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.admin_panel_list,parent,false));

            Name = (TextView) itemView.findViewById(R.id.tvName);
            Dob = (TextView) itemView.findViewById(R.id.tvDob);
            ID = (TextView) itemView.findViewById(R.id.tvID);
            Email = (TextView) itemView.findViewById(R.id.tvEmail);


            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ViewStudents.class);
                    intent.putExtra("key", key);
                    intent.putExtra("adminName", Name.getText().toString());
                    intent.putExtra("adminEmail", Email.getText().toString());
                    intent.putExtra("adminIdNumber", ID.getText().toString());
                    intent.putExtra("adminDOB", Dob.getText().toString());

                    mContext.startActivity(intent);
                }
            });*/

        }

        public void bind(UserProfile userProfile, String key){

            Name.setText(userProfile.getUserName());
            Email.setText(userProfile.getUserEmail());
            ID.setText(userProfile.getUserRegistrationNumber());
            Dob.setText(userProfile.getUserDOB());
            this.key=key;

        }
    }

    class StuAdapter extends RecyclerView.Adapter<stPanelRecyclerView.StudentListView>{
        private List<UserProfile> profile;
        private List<String> keys;

        public StuAdapter(List<UserProfile> profile, List<String> keys) {
            this.profile = profile;
            this.keys = keys;
        }

        @NonNull
        @Override
        public stPanelRecyclerView.StudentListView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new stPanelRecyclerView.StudentListView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull stPanelRecyclerView.StudentListView holder, int position) {
            holder.bind(profile.get(position),keys.get(position));
        }

        @Override
        public int getItemCount() {
            return profile.size();
        }
    }
    
}
