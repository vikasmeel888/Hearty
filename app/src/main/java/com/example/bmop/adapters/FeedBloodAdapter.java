package com.example.bmop.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bmop.R;
import com.example.bmop.activities.ChatActivity;
import com.example.bmop.models.Blood;
import com.example.bmop.models.User;
import com.example.bmop.utils.AppPreferences;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class FeedBloodAdapter extends FirestoreRecyclerAdapter<Blood, FeedBloodAdapter.BloodFeedViewHolder> {

    Context context;
    private FeedBloodAdapter.BloodFeedViewHolder holder;
    User user;
    public  FeedBloodAdapter(Context context, FirestoreRecyclerOptions<Blood> blood){
        super(blood);
        this.context = context;
        user = AppPreferences.getInstance().getUser();
    }

    @Override
    protected void onBindViewHolder(@NonNull FeedBloodAdapter.BloodFeedViewHolder holder, int position, @NonNull Blood model) {
        holder.setIsRecyclable(false);
        String Pnaam = "Patient Name: " + model.getPatientName();
        String PAge = "Age: " + model.getAge();
        String gender = "Gender: " + model.getSex();
        String BloodGroup = "Blood Group: " + model.getBloodGroup();
        String BloodLocation = "Location: " + model.getBloodLocation();
        String BloodUnits = "Blood Units: " + model.getBloodUnits();
        holder.patientName.setText(Pnaam);
        holder.age.setText(PAge);
        holder.sex.setText(gender);
        holder.bloodGroup.setText(BloodGroup);
        holder.bloodLocation.setText(BloodLocation);
        holder.bloodUnits.setText(BloodUnits);
        if(user.getId().equals(model.getBlood_uploaderId())){
            holder.chats.setVisibility(View.GONE);
        }else {
                holder.chats.setVisibility(View.VISIBLE);
            }
        holder.chats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                DocumentReference docRef = firebaseFirestore.collection("blood").document(model.blood_uploaderId);
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("id",model.blood_uploaderId);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public FeedBloodAdapter.BloodFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_containers,parent,false);
        return new  FeedBloodAdapter.BloodFeedViewHolder(view);
    }


    public class BloodFeedViewHolder extends RecyclerView.ViewHolder{
        TextView patientName;
        TextView age;
        TextView sex;
        TextView bloodGroup;
        TextView bloodLocation;
        TextView bloodUnits;
        Button chats;
        public BloodFeedViewHolder(View itemView){
            super(itemView);
            patientName = (TextView) itemView.findViewById(R.id.patientNames);
            age = (TextView) itemView.findViewById(R.id.age);
            sex = (TextView) itemView.findViewById(R.id.Sex);
            bloodGroup = (TextView) itemView.findViewById(R.id.BloodGroup);
            bloodLocation = (TextView) itemView.findViewById(R.id.location);
            bloodUnits = (TextView) itemView.findViewById(R.id.BloodUnits);
            chats = (Button) itemView.findViewById(R.id.chats);

        }
    }
}

