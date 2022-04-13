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
import com.example.bmop.models.Medicine;
import com.example.bmop.models.User;
import com.example.bmop.utils.AppPreferences;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class BloodActivity extends BaseActivity{
    private static final String TAG = "BloodActivity";
    User user;
    FirebaseFirestore firebaseFirestore;
    TextInputLayout searchBtn;
    FeedBloodAdapter adapter2;
    TextInputEditText searchName;
    RecyclerView recyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        searchBtn = findViewById(R.id.search_space2);
        searchName = findViewById(R.id.et_search_blood);
        searchBtn.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String blod = searchName.getText().toString().trim();
                if(blod.length() >= 1){
                    adapter2.stopListening();
                    Query query = firebaseFirestore.collection("blood").whereGreaterThanOrEqualTo("bloodGroup", blod)
                            .whereLessThanOrEqualTo("bloodGroup", blod + "\\uf8ff");
                    FirestoreRecyclerOptions<Blood> blood = new FirestoreRecyclerOptions.Builder<Blood>().setQuery(query, Blood.class).build();
                    adapter2 = new FeedBloodAdapter(BloodActivity.this, blood);
                    recyclerView2.setHasFixedSize(false);
                    recyclerView2.setLayoutManager(new LinearLayoutManager(BloodActivity.this));
                    recyclerView2.setAdapter(adapter2);
                    adapter2.startListening();
                }
            }
        });
        user = AppPreferences.getInstance().getUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView3);
        Query query = firebaseFirestore.collection("blood");
        FirestoreRecyclerOptions<Blood> blood = new FirestoreRecyclerOptions.Builder<Blood>().setQuery(query, Blood.class).build();
        adapter2 = new FeedBloodAdapter(this, blood);
        recyclerView2.setHasFixedSize(false);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setAdapter(adapter2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter2.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter2.stopListening();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_blood;
    }

    public void openDonateBlood(View view){
        Intent intent = new Intent(this, DonateBloodActivity.class);
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
