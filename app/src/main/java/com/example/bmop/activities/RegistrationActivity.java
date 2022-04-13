package com.example.bmop.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bmop.R;
import com.example.bmop.models.User;
import com.example.bmop.utils.AppPreferences;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.bmop.utils.Constants.USERS_COLLECTION;


public class RegistrationActivity extends BaseActivity {
    private static final String TAG = "RegistrationActivity";
    private static final String KEY_USER = "KEY_USER";
    private FirebaseFirestore db;
    private User user;
    String phoneno;
    TextInputEditText phoneNo;

    @BindView(R.id.til_first_name)
    TextInputLayout tilFirstName;

    @BindView(R.id.til_last_name)
    TextInputLayout tilLastName;

    @BindView(R.id.til_email)
    TextInputLayout tilEmail;

    @BindView(R.id.til_phone)
    TextInputLayout tilPhone;

    public static Intent start(Context context, User user) {
        Intent intent = new Intent(context, RegistrationActivity.class);
        intent.putExtra(KEY_USER, user);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        user = getIntent().getParcelableExtra(KEY_USER);
        phoneNo = (TextInputEditText) findViewById(R.id.editphone);

        tilFirstName.getEditText().setText(user.getFirstName());
        tilLastName.getEditText().setText(user.getLastName());
        tilEmail.getEditText().setText(user.getEmail());
//        phoneNo.getText().setText(phoneno);
//        tilPhone.getEditText().setText(user.getPhone());
//        user.setPhone(tilPhon.);
//        Map<String, Object> phone = new HashMap<>();
//        phone.put("phone",user.getPhone());
//        db.collection("users").document(user.getId()).set(phone);
    }

    @OnClick(R.id.btn_register)
    protected void registerUser() {
        phoneno = phoneNo.getText().toString();
        user.setPhone(phoneno);
        db.collection(USERS_COLLECTION).document(user.getId()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: ");
                Toast.makeText(RegistrationActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                AppPreferences.getInstance().saveUser(user);
                Intent intent = new Intent(RegistrationActivity.this, com.example.bmop.activities.HomeActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_registration;
    }
}

