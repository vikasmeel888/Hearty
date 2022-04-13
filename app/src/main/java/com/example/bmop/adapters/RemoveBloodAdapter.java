package com.example.bmop.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bmop.R;
import com.example.bmop.activities.ChatActivity;
import com.example.bmop.activities.RemoveActivity;
import com.example.bmop.models.Blood;
import com.example.bmop.models.Medicine;
import com.example.bmop.models.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Map;
import java.util.Set;

import static androidx.core.content.ContextCompat.startActivity;


public class RemoveBloodAdapter extends FirestoreRecyclerAdapter<Blood, RemoveBloodAdapter.RemoveBloodViewHolder> {
    Context context;
    public  RemoveBloodAdapter(Context context1, FirestoreRecyclerOptions<Blood> blood){
        super(blood);
        this.context = context1;

    }

    @Override
    protected void onBindViewHolder(RemoveBloodAdapter.RemoveBloodViewHolder holder, int position, Blood model) {
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
        holder.removes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "cheers!!!", Toast.LENGTH_SHORT).show();
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                firebaseFirestore.collection("blood").document(model.docId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context,"Deleted!",Toast.LENGTH_LONG).show();
                        notifyDataSetChanged();
                        ((RemoveActivity)context).afterRemove();
//                        Intent intent = new Intent(context, RemoveActivity.class);
//                        context.startActivity(intent);
                        //((Activity)context).recreate();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"Nope! Ille! Bass! Khatam! Tata! Bye Bye!",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

    @NonNull
    @Override
    public RemoveBloodAdapter.RemoveBloodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_container_remove,parent,false);
        return new RemoveBloodAdapter.RemoveBloodViewHolder(view);
    }


    public class RemoveBloodViewHolder extends RecyclerView.ViewHolder{
        TextView patientName;
        TextView age;
        TextView sex;
        TextView bloodGroup;
        TextView bloodLocation;
        TextView bloodUnits;
        Button removes;
        public RemoveBloodViewHolder(View itemView){
            super(itemView);
            patientName = (TextView) itemView.findViewById(R.id.patientNames);
            age = (TextView) itemView.findViewById(R.id.BloodGroup);
            sex = (TextView) itemView.findViewById(R.id.Sex);
            bloodGroup = (TextView) itemView.findViewById(R.id.age);
            bloodLocation = (TextView) itemView.findViewById(R.id.BloodUnits);
            bloodUnits = (TextView) itemView.findViewById(R.id.location);
            removes = (Button) itemView.findViewById(R.id.remove);
        }
    }
}

