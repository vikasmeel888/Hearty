package com.example.bmop.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.bmop.R;
import com.example.bmop.adapters.MessageAdapter;
import com.example.bmop.models.IDModelMsg;
import com.example.bmop.models.Message;
import com.example.bmop.models.User;
import com.example.bmop.utils.AppPreferences;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static com.example.bmop.utils.Constants.CHATS_COLLECTION;

public class MessagesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    User user;
    MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        recyclerView = findViewById(R.id.recyclerView);
        firebaseFirestore = FirebaseFirestore.getInstance();
        user = AppPreferences.getInstance().getUser();
        Query query = firebaseFirestore.collection(CHATS_COLLECTION).whereArrayContains("chatters",user.getId());
        FirestoreRecyclerOptions<IDModelMsg> options = new FirestoreRecyclerOptions.Builder<IDModelMsg>()
                .setQuery(query, IDModelMsg.class).build();
        messageAdapter = new MessageAdapter(this,options);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(MessagesActivity.this));
        recyclerView.setAdapter(messageAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        messageAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        messageAdapter.stopListening();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}