package com.example.bmop.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bmop.R;
import com.example.bmop.models.Medicine;
import com.example.bmop.models.User;
import com.example.bmop.utils.AppPreferences;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Transaction;
import butterknife.OnClick;

import static com.example.bmop.utils.Constants.MEDICINE_COLLECTION;
import static com.example.bmop.utils.Constants.USERS_COLLECTION;

public class SellMedicineActivity extends BaseActivity {
    private static final String TAG = "SellMedicineActivity";
    private static final String KEY_MEDICINE = "KEY_MEDICINE";
    private FirebaseFirestore db;
    private Medicine medicine;
    private User user;
    String uploader_id,uploader_name;


    String medname;
    private TextInputEditText medName;
    String medcompany;
    private TextInputEditText medCompany;
    String meddose;
    private TextInputEditText medDose;
    String medprice;
    TextInputEditText medPrice;
    String medcategory;
    TextInputEditText medCategory;
    String medloc;
    TextInputEditText medLoc;



    public static Intent start(Context context, Medicine medicine) {
        Intent intent = new Intent(context, SellMedicineActivity.class);
        intent.putExtra(KEY_MEDICINE, medicine);
        return intent;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        medicine = getIntent().getParcelableExtra(KEY_MEDICINE);
        user = AppPreferences.getInstance().getUser();
        uploader_id = user.getId();
        uploader_name = user.getFirstName()+" "+user.getLastName();
        medName = (TextInputEditText) findViewById(R.id.til_med_name);
        medCompany = (TextInputEditText) findViewById(R.id.til_med_company);
        medDose = (TextInputEditText) findViewById(R.id.til_med_dose);
        medPrice = (TextInputEditText) findViewById(R.id.til_med_price);
        medCategory = (TextInputEditText) findViewById(R.id.til_med_category);
        medLoc = (TextInputEditText) findViewById(R.id.til_med_loc);
    }


    @OnClick(R.id.btn_submit)
    protected void submitMedicineInfo() {
        medname = medName.getText().toString();
        medcompany = medCompany.getText().toString();
        meddose = medDose.getText().toString();
        medprice = medPrice.getText().toString();
        medcategory = medCategory.getText().toString();
        medloc = medLoc.getText().toString();
        if(medname.isEmpty() ||medcompany.isEmpty()|| meddose.isEmpty() || medprice.isEmpty()||  medcategory.isEmpty()||  medloc.isEmpty()){
            Toast.makeText(this, "fill all the fields", Toast.LENGTH_SHORT).show();
        }else{
            Medicine medicine = new Medicine(medname,medcompany,meddose,medprice,medcategory,medloc,uploader_id);
            CollectionReference collectionReference = db.collection(MEDICINE_COLLECTION);
            collectionReference.add(medicine).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(TAG, "onSuccess: ");
                    documentReference.update("docId",documentReference.getId());
//                    documentReference.update("docId",documentReference.getId());
                    Toast.makeText(SellMedicineActivity.this, "Submitted", Toast.LENGTH_SHORT).show();
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
    protected int getLayout() {
        return R.layout.activity_seel_medicine;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
