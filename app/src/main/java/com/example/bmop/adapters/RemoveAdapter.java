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


public class RemoveAdapter extends FirestoreRecyclerAdapter<Medicine, RemoveAdapter.RemoveViewHolder> {
    Context context;
    public  RemoveAdapter(Context context1, FirestoreRecyclerOptions<Medicine> medicine){
        super(medicine);
        this.context = context1;

    }

    @Override
    protected void onBindViewHolder(RemoveAdapter.RemoveViewHolder holder, int position, Medicine model) {
        holder.setIsRecyclable(false);
        String naam = "Name: " + model.getMedName();
        String dosage = "Dose: " + model.getMedDose();
        String Company = "Company: " + model.getMedCompany();
        String Price = "Price: " + model.getMedPrice();
        String Category = "Category: " + model.getMedCategory();
        String Loc = "Location: " + model.getMedLoc();
        holder.name.setText(naam);
        holder.dose.setText(dosage);
        holder.company.setText(Company);
        holder.price.setText(Price);
        holder.medCategory.setText(Category);
        holder.medLoc.setText(Loc);
        holder.removes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "cheers!!!", Toast.LENGTH_SHORT).show();
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                firebaseFirestore.collection("medicine").document(model.docId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public RemoveAdapter.RemoveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_container_remove,parent,false);
        return new RemoveAdapter.RemoveViewHolder(view);
    }


    public class RemoveViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView company;
        TextView dose;
        TextView price;
        TextView medCategory;
        TextView medLoc;
        Button removes;
        public RemoveViewHolder(View itemView){
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.patientNames);
            company = (TextView) itemView.findViewById(R.id.BloodGroup);
            dose = (TextView) itemView.findViewById(R.id.Sex);
            price = (TextView) itemView.findViewById(R.id.age);
            medCategory = (TextView) itemView.findViewById(R.id.BloodUnits);
            medLoc = (TextView) itemView.findViewById(R.id.location);
            removes = (Button) itemView.findViewById(R.id.remove);
        }
    }
}

