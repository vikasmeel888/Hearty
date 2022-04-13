package com.example.bmop.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bmop.R;
import com.example.bmop.models.User;
import com.example.bmop.utils.AppPreferences;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.OnClick;

import static com.example.bmop.utils.Constants.RC_SIGN_IN;


public class WelcomeActivity extends BaseActivity {
    private static final String TAG = "WelcomeActivity";
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        if(AppPreferences.getInstance().isLogin()){
            Intent intent = new Intent(WelcomeActivity.this, com.example.bmop.activities.HomeActivity.class);
            startActivity(intent);
            finish();
        }else{
            db = FirebaseFirestore.getInstance();
            // Configure Google Sign In
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        }
    }

    public static Intent start(Context context) {
        Intent intent = new Intent(context, WelcomeActivity.class);
        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @OnClick(R.id.btn_google)
    protected void onClickGoogle() {
        Log.d(TAG, "onClickGoogle: ");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                Log.d(TAG, "firebaseAuthWithGoogle: " + account.getId());

                String googleId;
                googleId = account.getId();
                assert googleId != null;
                DocumentReference userDocument = db.collection("users").document(googleId);
                userDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            AppPreferences.getInstance().setLogin();
                            DocumentSnapshot snapshot = task.getResult();
                            if (snapshot != null && snapshot.exists()) {
                                User user = snapshot.toObject(User.class);
                                Log.d(TAG, "DocumentSnapshot data: " + user);
                                AppPreferences.getInstance().saveUser(user);
                                Intent intent = new Intent(WelcomeActivity.this, com.example.bmop.activities.HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Log.d(TAG, "No such document, so register");
                                User user = new User();
                                user.setFirstName(account.getGivenName());
                                user.setLastName(account.getFamilyName());
                                user.setId(account.getId());
                                user.setEmail(account.getEmail());
//                                user.setPhone(account.getPhone());
                                Intent intent = com.example.bmop.activities.RegistrationActivity.start(WelcomeActivity.this, user);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Log.e(TAG, "onComplete: failed!", task.getException());
                        }
                    }
                });
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);

            }
        }
    }
}