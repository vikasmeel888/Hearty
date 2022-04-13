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
import com.example.bmop.models.Equipment;
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


public class RemoveEquipAdapter extends FirestoreRecyclerAdapter<Equipment, RemoveEquipAdapter.RemoveEquipViewHolder> {
    Context context;
    public  RemoveEquipAdapter(Context context1, FirestoreRecyclerOptions<Equipment> equipment){
        super(equipment);
        this.context = context1;

    }

    @Override
    protected void onBindViewHolder(RemoveEquipAdapter.RemoveEquipViewHolder holder, int position, Equipment model) {
        holder.setIsRecyclable(false);
        String eNaam = "Equipment Name: " + model.getEquipName();
        String eCompany = "Company: " + model.getEquipCompany();
        String eQuantity = "Quantity: " + model.getEquipQuantity();
        String eCategory = "Category: " + model.getEquipCategory();
        String eLoc = "Location: " + model.getEquipLoc();
        String ePrice = "Price: " + model.getEquipPrice();
        holder.equipName.setText(eNaam);
        holder.equipCompany.setText(eCompany);
        holder.equipQuantity.setText(eQuantity);
        holder.equipCategory.setText(eCategory);
        holder.equipLoc.setText(eLoc);
        holder.equipPrice.setText(ePrice);
        holder.removes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "cheers!!!", Toast.LENGTH_SHORT).show();
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                firebaseFirestore.collection("equipment").document(model.docId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public RemoveEquipAdapter.RemoveEquipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_container_remove,parent,false);
        return new RemoveEquipAdapter.RemoveEquipViewHolder(view);
    }


    public class RemoveEquipViewHolder extends RecyclerView.ViewHolder{
        TextView equipName;
        TextView equipCompany;
        TextView equipQuantity;
        TextView equipCategory;
        TextView equipLoc;
        TextView equipPrice;
        Button removes;
        public RemoveEquipViewHolder(View itemView){
            super(itemView);
            equipName = (TextView) itemView.findViewById(R.id.patientNames);
            equipCompany = (TextView) itemView.findViewById(R.id.BloodGroup);
            equipQuantity = (TextView) itemView.findViewById(R.id.Sex);
            equipCategory = (TextView) itemView.findViewById(R.id.age);
            equipLoc = (TextView) itemView.findViewById(R.id.BloodUnits);
            equipPrice = (TextView) itemView.findViewById(R.id.location);
            removes = (Button) itemView.findViewById(R.id.remove);
        }
    }
}

