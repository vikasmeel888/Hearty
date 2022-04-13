package com.example.bmop.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bmop.models.Medicine;
import com.example.bmop.models.User;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
//import com.google.firebase.functions.FirebaseFunctions;
//import com.google.firebase.functions.HttpsCallableResult;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.math.BigInteger;
import java.security.Permissions;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.bmop.adapters.*;
import butterknife.BindView;
import butterknife.OnClick;
import com.example.bmop.R;
import com.example.bmop.adapters.ChatAdapter;
import com.example.bmop.models.Message;
//import jtayu.app.models.Permissions;
import com.example.bmop.models.Message;
import com.example.bmop.utils.AppPreferences;
//import com.example.bmop.utils.AppUtils;
//import pub.devrel.easypermissions.AfterPermissionGranted;
//import pub.devrel.easypermissions.EasyPermissions;

import static com.example.bmop.utils.Constants.CHATS_COLLECTION;
import static com.example.bmop.utils.Constants.CHAT_IMAGES_STORAGE;
import static com.example.bmop.utils.Constants.MESSAGES_COLLECTION;
import static com.example.bmop.utils.Constants.REQUEST_GALLERY;
import static com.example.bmop.utils.Constants.REQUEST_STORAGE;

//import static jtayu.app.activities.MessageActivity.flag;

public class ChatActivity extends BaseActivity {
    private static final String TAG = "ChatActivity";
    private static final String KEY_USER = "KEY_USER";
    public static final String KEY_MESSAGE = "KEY_MESSAGE";
    private static User destUser;
    private String lastId = "0";
    private ListView lv;
    private EditText editText;
    private ArrayAdapter<String> adapter2;
    private ArrayAdapter<String> adapter1;
    private String permissionDocumentid;
    private String convoDocumentid;


    private User user;
    private User destperson;
    private Medicine medicine;
//    private Medicine uploaderId;
    private ChatAdapter adapter;
    private FirebaseFirestore firebaseFirestore;
    //    private FirebaseFunctions firebaseFunctions;
    private String chatId;
    private static String firstId,secondId;
    private static String firstName,secondName ;
    private String person2;
    private String destName = "XYZ";


    @BindView(R.id.et_message)
    EditText etMessage;

    @BindView(R.id.recycler_chat)
    RecyclerView chatList;

    public static Intent start(Context context) {
        Intent intent = new Intent(context, ChatActivity.class);
        //intent.putExtra(KEY_USER, destinationUser);
        return intent;
    }

//    public static Intent start(Context context) {
//        Intent intent = new Intent(context, ChatActivity.class);
////        intent.putExtra(KEY_USER, destinationUser);
////        intent.putExtra(KEY_MESSAGE, forwardMessage);
//        return intent;
//    }

    @Override
    protected int getLayout() {
        return R.layout.activity_chat;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
//        firebaseFunctions = FirebaseFunctions.getInstance();
        // Create a storage reference from our app

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user = AppPreferences.getInstance().getUser();
        person2 = getIntent().getStringExtra("id");

//        person2 = getIntent().getParcelableExtra(KEY_USER);
        if (person2 == null) {
            Log.e(TAG, "onCreate: destinationUser required");
            return;
        }
        chatId = getChatId(user.getId(), person2);
//        chatId = user.getId();
        if (chatId == null) {
            Log.e(TAG, "onCreate: can not initiate a chat");
            return;
        }
        Log.i(TAG, "THE chatId is :" + chatId);
        //getChatMessages(chatId);
        firebaseFirestore = FirebaseFirestore.getInstance();
        chatList = findViewById(R.id.recycler_chat);
        Query query = firebaseFirestore.collection(CHATS_COLLECTION).document(chatId).collection(MESSAGES_COLLECTION).orderBy("sentOn", Query.Direction.ASCENDING);
        Log.d(TAG, "QUERY " + query.toString());
        FirestoreRecyclerOptions<Message> options = new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class).build();

        adapter = new ChatAdapter(this, options);
        //adapter.setOnChatItemLongClickListener(this);
        chatList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        chatList.setLayoutManager(linearLayoutManager);
        chatList.setAdapter(adapter);
        Log.d(TAG, "END OF RECYCLER VIEW");

        firebaseFirestore.collection("users").document(person2).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot!=null && documentSnapshot.exists()){
                        destperson = documentSnapshot.toObject(User.class);
                        destName = destperson.getFirstName()+" "+destperson.getLastName();
                        getSupportActionBar().setTitle(destName);
                    }
                    else {
                        Log.d(TAG, "onComplete: Destination User Ille!");
                    }
                }
            }
        });

    }



    private void sendToFirebase(String value) {
        Log.d(TAG, "sendToFirebase:  VALUE === " + value);
        Message message;
            message = new Message(value,user.getId(), new Timestamp(new Date()));
        firebaseFirestore.collection(CHATS_COLLECTION).document(chatId).collection(MESSAGES_COLLECTION)
                .add(message).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                List<String> list = new ArrayList<>();
                list.add(firstId);
                list.add(secondId);
                Map<String, Object> map = new HashMap<>();
                map.put("chatters",list);
                firebaseFirestore.collection(CHATS_COLLECTION).document(chatId).set(map);
                Log.d(TAG, "onSuccess: ");
                etMessage.setText(null);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: ");
            }
        });
        chatList.smoothScrollToPosition(adapter.getItemCount());
    }


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (chatId == null) {
            Toast.makeText(this, "Self chatting not available yet!", Toast.LENGTH_SHORT).show();
        } else {
            adapter.startListening();
        }
    }

    @OnClick(R.id.btn_send)
    protected void onClickSendMessage() {
        sendToFirebase(etMessage.getEditableText().toString());
    }

    public static String getChatId(String sourceUserId, String destinationUserId) {
        BigInteger num = new BigInteger(sourceUserId);
        BigInteger num2 = new BigInteger(destinationUserId);

        int val = num.compareTo(num2);
        if (val == 0) {
            Log.d(TAG, "getChatId: Same Ids");
            return null;
        } else if (val > 0) {
            Log.d(TAG, "getChatId: sourceId > destinationId");
            firstId = destinationUserId;
            secondId = sourceUserId;
            return destinationUserId + "_" + sourceUserId;
        } else {
            Log.d(TAG, "getChatId: sourceId < destinationId");
            firstId = sourceUserId;
            secondId = destinationUserId;
            return sourceUserId + "_" + destinationUserId;
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



