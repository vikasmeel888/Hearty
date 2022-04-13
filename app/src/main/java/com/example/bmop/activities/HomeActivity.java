package com.example.bmop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.bmop.R;
import com.example.bmop.utils.AppPreferences;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class HomeActivity extends BaseActivity {
    private static final String TAG = "HomeActivity";
    private static final String KEY_NAME = "KEY_NAME";
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    public void openMedicineSearchActivity(View view){
        Intent intent = new Intent(this, MedicineActivity.class);
        startActivity(intent);
    }

    public void openBloodActivity(View view){
        Intent intent = new Intent(this, BloodActivity.class);
        startActivity(intent);
    }

    public void openEquipmentActivity(View view){
        Intent intent = new Intent(this, EquipmentActivity.class);
        startActivity(intent);
    }

    public void openRemove(View view){
        Intent intent = new Intent(this, RemoveActivity.class);
        startActivity(intent);
    }
    public void messages(View view){
        Intent intent = new Intent(this, MessagesActivity.class);
        startActivity(intent);
    }

    public void signOut(View view) {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        AppPreferences appPreferences = new AppPreferences();
                        appPreferences.Initialize(HomeActivity.this);
                        appPreferences.onLogout();
                        Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });
    }
}
