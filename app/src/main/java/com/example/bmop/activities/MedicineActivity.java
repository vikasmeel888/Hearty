package com.example.bmop.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bmop.R;
import com.example.bmop.adapters.FeedAdapter;
import com.example.bmop.models.Medicine;
import com.example.bmop.models.User;
import com.example.bmop.utils.AppPreferences;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MedicineActivity extends BaseActivity{
    private static final String TAG = "SearchMedicineActivity";

    User user;
    FirebaseFirestore firebaseFirestore;
    TextInputLayout searchBtn;
    FeedAdapter adapter;
    TextInputEditText searchName;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setContentView(R.layout.activity_medicine);
        searchBtn = findViewById(R.id.search_space);
        searchName = findViewById(R.id.et_search);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        searchBtn.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String med = searchName.getText().toString().trim();
                if(med.length() >= 1){
                    adapter.stopListening();
                    recyclerView.setAdapter(null);
                    Query query = firebaseFirestore.collection("medicine").whereGreaterThanOrEqualTo("medName", med)
                            .whereLessThanOrEqualTo("medName", med + "\\uf8ff");
                    FirestoreRecyclerOptions<Medicine> medicine = new FirestoreRecyclerOptions.Builder<Medicine>().setQuery(query, Medicine.class).build();
                    adapter = new FeedAdapter(MedicineActivity.this, medicine);
                    recyclerView.setHasFixedSize(false);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MedicineActivity.this));
                    recyclerView.setAdapter(adapter);
                    adapter.startListening();
                }
            }
        });
        user = AppPreferences.getInstance().getUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query = firebaseFirestore.collection("medicine");
        FirestoreRecyclerOptions<Medicine> medicine = new FirestoreRecyclerOptions.Builder<Medicine>().setQuery(query, Medicine.class).build();
        adapter = new FeedAdapter(this, medicine);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void openSellMedicine(View v){
        Intent intent = new Intent(this, SellMedicineActivity.class);
        startActivity(intent);
//        finish();

    }

//    public void message(View v){
//        Intent intent = new Intent(this, ChatActivity.class);
//        startActivity(intent);
//        finish();
//    }


    @Override
    protected int getLayout() {
        return R.layout.activity_medicine;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

