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
import com.example.bmop.models.Equipment;
import com.example.bmop.models.Medicine;
import com.example.bmop.models.User;
import com.example.bmop.utils.AppPreferences;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.core.content.ContextCompat.startActivity;


public class FeedEquipmentAdapter extends FirestoreRecyclerAdapter<Equipment, FeedEquipmentAdapter.EquipmentFeedViewHolder> {

    Context context;
    User user;
    private FeedEquipmentAdapter.EquipmentFeedViewHolder holder;
    public  FeedEquipmentAdapter(Context context, FirestoreRecyclerOptions<Equipment> equipment){
        super(equipment);
        this.context = context;
        user = AppPreferences.getInstance().getUser();
    }

    @Override
    protected void onBindViewHolder(@NonNull FeedEquipmentAdapter.EquipmentFeedViewHolder holder, int position, @NonNull Equipment model) {
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
        if(user.getId().equals(model.getEquip_uploaderId())){
            holder.chat.setVisibility(View.GONE);
        }else {
            holder.chat.setVisibility(View.VISIBLE);
        }
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                DocumentReference docRef = firebaseFirestore.collection("equipment").document(model.equip_uploaderId);
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("id",model.equip_uploaderId);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public FeedEquipmentAdapter.EquipmentFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_containers,parent,false);
        return new FeedEquipmentAdapter.EquipmentFeedViewHolder(view);
    }


    public class EquipmentFeedViewHolder extends RecyclerView.ViewHolder{
        TextView equipName;
        TextView equipCompany;
        TextView equipQuantity;
        TextView equipPrice;
        TextView equipCategory;
        TextView equipLoc;
        Button chat;
        public EquipmentFeedViewHolder(View itemView){
            super(itemView);
            equipName = (TextView) itemView.findViewById(R.id.patientNames);
            equipCompany = (TextView) itemView.findViewById(R.id.BloodGroup);
            equipQuantity = (TextView) itemView.findViewById(R.id.Sex);
            equipPrice = (TextView) itemView.findViewById(R.id.age);
            equipCategory = (TextView) itemView.findViewById(R.id.BloodUnits);
            equipLoc = (TextView) itemView.findViewById(R.id.location);
            chat = (Button) itemView.findViewById(R.id.chats);

        }
    }
}

