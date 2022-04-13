package com.example.bmop.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bmop.R;
import com.example.bmop.models.Blood;
import com.example.bmop.models.Equipment;
import com.example.bmop.models.User;
import com.example.bmop.utils.AppPreferences;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.OnClick;

import static com.example.bmop.utils.Constants.BLOOD_COLLECTION;
import static com.example.bmop.utils.Constants.EQUIPMENT_COLLECTION;

public class DonateBloodActivity extends BaseActivity{
    private static final String TAG = "DonateBloodActivity";
    private static final String KEY_BLOOD = "KEY_BLOOD";
    private FirebaseFirestore db;
    private Blood blood;
    private User user;
    private String blood_uploaderid;


    String patientname;
    private TextInputEditText patientName;
    String patientage;
    private TextInputEditText patientAge;
    String patientsex;
    private TextInputEditText patientSex;
    String bloodgroup;
    private TextInputEditText bloodGroup;
    String bloodlocation;
    private TextInputEditText bloodLocation;
    String bloodunit;
    private TextInputEditText bloodUnit;

    public static Intent start(Context context, Blood blood) {
        Intent intent = new Intent(context, DonateBloodActivity.class);
        intent.putExtra(KEY_BLOOD, blood);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        db = FirebaseFirestore.getInstance();
        blood = getIntent().getParcelableExtra(KEY_BLOOD);
        user = AppPreferences.getInstance().getUser();
        blood_uploaderid = user.getId();
        patientName = (TextInputEditText) findViewById(R.id.til_patient_name);
        patientAge = (TextInputEditText) findViewById(R.id.til_Age);
        patientSex = (TextInputEditText) findViewById(R.id.til_Sex);
        bloodGroup = (TextInputEditText) findViewById(R.id.til_blood_group);
        bloodLocation = (TextInputEditText) findViewById(R.id.til_blood_location);
        bloodUnit = (TextInputEditText) findViewById(R.id.til_blood_units);
    }

    @OnClick(R.id.btn_submit_blood)
    protected void submitEquipmentInfo() {
        patientname = patientName.getText().toString();
        patientage = patientAge.getText().toString();
        patientsex = patientSex.getText().toString();
        bloodgroup = bloodGroup.getText().toString();
        bloodlocation = bloodLocation.getText().toString();
        bloodunit = bloodUnit.getText().toString();
        if(patientname.isEmpty() || patientage.isEmpty() || patientsex.isEmpty() || bloodgroup.isEmpty() || bloodlocation.isEmpty() || bloodunit.isEmpty()){
            Toast.makeText(this, "fill all the fields", Toast.LENGTH_SHORT).show();
        }else{
            Blood blood = new Blood(patientname, patientage, patientsex, bloodgroup, bloodlocation, bloodunit, blood_uploaderid);
            CollectionReference collectionReference = db.collection(BLOOD_COLLECTION);
            collectionReference.add(blood).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(TAG, "onSuccess: ");
                    documentReference.update("docId",documentReference.getId());
                    Toast.makeText(DonateBloodActivity.this, "Submitted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: ");
                }
            });
        }

    }

    @Override
    protected int getLayout(){ return R.layout.activity_donate_blood; }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
