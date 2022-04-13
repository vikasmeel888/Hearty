package com.example.bmop.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bmop.R;
import com.example.bmop.adapters.FeedAdapter;
import com.example.bmop.adapters.RemoveAdapter;
import com.example.bmop.adapters.RemoveBloodAdapter;
import com.example.bmop.adapters.RemoveEquipAdapter;
import com.example.bmop.models.Blood;
import com.example.bmop.models.Equipment;
import com.example.bmop.models.Medicine;
import com.example.bmop.models.User;
import com.example.bmop.utils.AppPreferences;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class RemoveActivity extends BaseActivity {
    User user;
    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;
    RemoveAdapter removeAdapter;
    RemoveBloodAdapter removeBloodAdapter;
    RemoveEquipAdapter removeEquipAdapter;
    String xyz = "medicine";
    ChipGroup chgrp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        user = AppPreferences.getInstance().getUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.recycling);
        chgrp = findViewById(R.id.chipgroup);
        chgrp.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Query query;
                if(xyz.equals("medicine")){
                    removeAdapter.stopListening();
                }else if(xyz.equals("blood")){
                    removeBloodAdapter.stopListening();
                }else{
                    removeEquipAdapter.stopListening();
                }
                switch (checkedId) {
                    case R.id.med:
                        xyz = "medicine";
                        query = firebaseFirestore.collection(xyz).whereEqualTo("uploaderId", user.getId());
                        FirestoreRecyclerOptions<Medicine> medicine = new FirestoreRecyclerOptions.Builder<Medicine>().setQuery(query, Medicine.class).build();
                        removeAdapter = new RemoveAdapter(RemoveActivity.this, medicine);
                        recyclerView.setHasFixedSize(false);
                        recyclerView.setLayoutManager(new LinearLayoutManager(RemoveActivity.this));
                        recyclerView.setAdapter(removeAdapter);
                        removeAdapter.startListening();
                        removeAdapter.notifyDataSetChanged();
                        break;
                    case R.id.equip:
                        xyz = "equipment";
                        query = firebaseFirestore.collection(xyz).whereEqualTo("equip_uploaderId", user.getId());
                        FirestoreRecyclerOptions<Equipment> equipment = new FirestoreRecyclerOptions.Builder<Equipment>().setQuery(query, Equipment.class).build();
                        removeEquipAdapter = new RemoveEquipAdapter(RemoveActivity.this, equipment);
                        recyclerView.setHasFixedSize(false);
                        recyclerView.setLayoutManager(new LinearLayoutManager(RemoveActivity.this));
                        recyclerView.setAdapter(removeEquipAdapter);
                        removeEquipAdapter.startListening();
                        removeEquipAdapter.notifyDataSetChanged();
                        break;
                    case R.id.blood:
                        xyz = "blood";
                        query = firebaseFirestore.collection(xyz).whereEqualTo("blood_uploaderId", user.getId());
                        FirestoreRecyclerOptions<Blood> blood = new FirestoreRecyclerOptions.Builder<Blood>().setQuery(query, Blood.class).build();
                        removeBloodAdapter = new RemoveBloodAdapter(RemoveActivity.this, blood);
                        recyclerView.setHasFixedSize(false);
                        recyclerView.setLayoutManager(new LinearLayoutManager(RemoveActivity.this));
                        recyclerView.setAdapter(removeBloodAdapter);
                        removeBloodAdapter.startListening();
                        removeBloodAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });
        Query query = firebaseFirestore.collection(xyz).whereEqualTo("uploaderId", user.getId());
        FirestoreRecyclerOptions<Medicine> medicine = new FirestoreRecyclerOptions.Builder<Medicine>().setQuery(query, Medicine.class).build();
        removeAdapter = new RemoveAdapter(RemoveActivity.this, medicine);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(removeAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        removeAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeAdapter.stopListening();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_post;
    }

    public void afterRemove(){
        Query query;
        switch (xyz) {
            case "medicine":
                removeAdapter.stopListening();
                query = firebaseFirestore.collection(xyz).whereEqualTo("uploaderId", user.getId());
                FirestoreRecyclerOptions<Medicine> medicine = new FirestoreRecyclerOptions.Builder<Medicine>().setQuery(query, Medicine.class).build();
                removeAdapter = new RemoveAdapter(RemoveActivity.this, medicine);
                recyclerView.setHasFixedSize(false);
                recyclerView.setLayoutManager(new LinearLayoutManager(RemoveActivity.this));
                recyclerView.setAdapter(removeAdapter);
                removeAdapter.startListening();
                removeAdapter.notifyDataSetChanged();
                break;
            case "equipment":
                removeEquipAdapter.stopListening();
                query = firebaseFirestore.collection(xyz).whereEqualTo("equip_uploaderId", user.getId());
                FirestoreRecyclerOptions<Equipment> equipment = new FirestoreRecyclerOptions.Builder<Equipment>().setQuery(query, Equipment.class).build();
                removeEquipAdapter = new RemoveEquipAdapter(RemoveActivity.this, equipment);
                recyclerView.setHasFixedSize(false);
                recyclerView.setLayoutManager(new LinearLayoutManager(RemoveActivity.this));
                recyclerView.setAdapter(removeEquipAdapter);
                removeEquipAdapter.startListening();
                removeEquipAdapter.notifyDataSetChanged();
                break;
            case "blood":
                removeBloodAdapter.stopListening();
                query = firebaseFirestore.collection(xyz).whereEqualTo("blood_uploaderId", user.getId());
                FirestoreRecyclerOptions<Blood> blood = new FirestoreRecyclerOptions.Builder<Blood>().setQuery(query, Blood.class).build();
                removeBloodAdapter = new RemoveBloodAdapter(RemoveActivity.this, blood);
                recyclerView.setHasFixedSize(false);
                recyclerView.setLayoutManager(new LinearLayoutManager(RemoveActivity.this));
                recyclerView.setAdapter(removeBloodAdapter);
                removeBloodAdapter.startListening();
                removeBloodAdapter.notifyDataSetChanged();
                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
