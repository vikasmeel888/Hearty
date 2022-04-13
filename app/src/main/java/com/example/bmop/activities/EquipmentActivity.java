package com.example.bmop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bmop.R;
import com.example.bmop.adapters.FeedAdapter;
import com.example.bmop.adapters.FeedBloodAdapter;
import com.example.bmop.adapters.FeedEquipmentAdapter;
import com.example.bmop.models.Blood;
import com.example.bmop.models.Equipment;
import com.example.bmop.models.Medicine;
import com.example.bmop.models.User;
import com.example.bmop.utils.AppPreferences;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class EquipmentActivity extends BaseActivity{
    private static final String TAG = "EquipmentActivity";
//    private static final String KEY_NAME = "KEY_NAME";
    User user;
    FirebaseFirestore firebaseFirestore;
    TextInputLayout searchBtn;
    FeedEquipmentAdapter adapter1;
    TextInputEditText searchName;
    RecyclerView recyclerView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setContentView(R.layout.activity_equipment);
        searchBtn = findViewById(R.id.search_space1);
        searchName = findViewById(R.id.et_search_equip);
        searchBtn.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String equip = searchName.getText().toString().trim();
                if(equip.length() >= 1){
                    adapter1.stopListening();
                    Query query = firebaseFirestore.collection("equipment").whereGreaterThanOrEqualTo("equipName", equip)
                            .whereLessThanOrEqualTo("equipName", equip + "\\uf8ff");
                    FirestoreRecyclerOptions<Equipment> equipment = new FirestoreRecyclerOptions.Builder<Equipment>().setQuery(query, Equipment.class).build();
                    adapter1 = new FeedEquipmentAdapter(EquipmentActivity.this, equipment);
                    recyclerView1.setHasFixedSize(false);
                    recyclerView1.setLayoutManager(new LinearLayoutManager(EquipmentActivity.this));
                    recyclerView1.setAdapter(adapter1);
                    adapter1.startListening();
                }
            }
        });
        user = AppPreferences.getInstance().getUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerView2);
        Query query = firebaseFirestore.collection("equipment");
        FirestoreRecyclerOptions<Equipment> equipment = new FirestoreRecyclerOptions.Builder<Equipment>().setQuery(query, Equipment.class).build();
        adapter1 = new FeedEquipmentAdapter(this, equipment);
        recyclerView1.setHasFixedSize(false);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1.setAdapter(adapter1);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter1.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter1.stopListening();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_equipment;
    }

    public void openDonateSellEquipment(View view){
        Intent intent = new Intent(this, SellEquipmentActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
