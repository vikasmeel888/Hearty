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
import com.example.bmop.models.Medicine;
import com.example.bmop.models.User;
import com.example.bmop.utils.AppPreferences;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.core.content.ContextCompat.startActivity;


public class FeedAdapter extends FirestoreRecyclerAdapter<Medicine, FeedAdapter.FeedViewHolder> {
        Context context;
        User user;
        private FeedAdapter.FeedViewHolder holder;
        public  FeedAdapter(Context context, FirestoreRecyclerOptions<Medicine> medicine){
            super(medicine);
            this.context = context;
            user = AppPreferences.getInstance().getUser();
        }

        @Override
        protected void onBindViewHolder(@NonNull FeedAdapter.FeedViewHolder holder, int position, @NonNull Medicine model) {
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
            if(user.getId().equals(model.getUploaderId())){
                holder.chat.setVisibility(View.GONE);
            }else {
                holder.chat.setVisibility(View.VISIBLE);
            }
            holder.chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                    DocumentReference docRef = firebaseFirestore.collection("medicine").document(model.uploaderId);
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("id",model.uploaderId);
                    context.startActivity(intent);
                }
            });
        }

        @NonNull
        @Override
        public FeedAdapter.FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_containers,parent,false);
            return new FeedAdapter.FeedViewHolder(view);
        }


        public class FeedViewHolder extends RecyclerView.ViewHolder{
            TextView name;
            TextView company;
            TextView dose;
            TextView price;
            TextView medCategory;
            TextView medLoc;
            Button chat;
            public FeedViewHolder(View itemView){
                super(itemView);
                 name = (TextView) itemView.findViewById(R.id.patientNames);
                 company = (TextView) itemView.findViewById(R.id.BloodGroup);
                 dose = (TextView) itemView.findViewById(R.id.Sex);
                 price = (TextView) itemView.findViewById(R.id.age);
                 medCategory = (TextView) itemView.findViewById(R.id.BloodUnits);
                 medLoc = (TextView) itemView.findViewById(R.id.location);
                 chat = (Button) itemView.findViewById(R.id.chats);
            }
        }
    }

