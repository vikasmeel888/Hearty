package com.example.bmop.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bmop.R;
import com.example.bmop.models.Equipment;
import com.example.bmop.models.Medicine;
import com.example.bmop.models.User;
import com.example.bmop.utils.AppPreferences;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.OnClick;

import static com.example.bmop.utils.Constants.EQUIPMENT_COLLECTION;
import static com.example.bmop.utils.Constants.MEDICINE_COLLECTION;

public class SellEquipmentActivity extends BaseActivity {
    private static final String TAG = "DonateSellEquipmentActivity";
    private static final String KEY_EQUIPMENT = "KEY_EQUIPMENT";
    private FirebaseFirestore db;
    private Equipment equipment;
    private User user;
    String equip_uploaderid;

    String equipname;
    private TextInputEditText equipName;
    String equipcompany;
    private TextInputEditText equipCompany;
    String equipquantity;
    private TextInputEditText equipQuantity;
    String equipprice;
    TextInputEditText equipPrice;
    String equipcategory;
    TextInputEditText equipCategory;
    String equiploc;
    TextInputEditText equipLoc;

    public static Intent start(Context context, Equipment equipment) {
        Intent intent = new Intent(context, SellEquipmentActivity.class);
        intent.putExtra(KEY_EQUIPMENT, equipment);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        db = FirebaseFirestore.getInstance();
        equipment = getIntent().getParcelableExtra(KEY_EQUIPMENT);
        user = AppPreferences.getInstance().getUser();
        equip_uploaderid = user.getId();
        equipName = (TextInputEditText) findViewById(R.id.til_equip_name);
        equipCompany = (TextInputEditText) findViewById(R.id.til_equip_company);
        equipQuantity = (TextInputEditText) findViewById(R.id.til_equip_quantity);
        equipPrice = (TextInputEditText) findViewById(R.id.til_equip_price);
        equipCategory = (TextInputEditText) findViewById(R.id.til_equip_category);
        equipLoc = (TextInputEditText) findViewById(R.id.til_equip_loc);
    }

    @OnClick(R.id.btn_submit_equip)
    protected void submitEquipmentInfo() {
        equipname = equipName.getText().toString();
        equipcompany = equipCompany.getText().toString();
        equipquantity = equipQuantity.getText().toString();
        equipprice = equipPrice.getText().toString();
        equipcategory = equipCategory.getText().toString();
        equiploc = equipLoc.getText().toString();
        if(equipname.isEmpty() || equipcompany.isEmpty() || equipquantity.isEmpty() || equipprice.isEmpty() || equipcategory.isEmpty() || equiploc.isEmpty()){
            Toast.makeText(this, "fill all the fields", Toast.LENGTH_SHORT).show();
        }else{
            Equipment equipment = new Equipment(equipname, equipcompany, equipquantity, equipprice, equipcategory, equiploc, equip_uploaderid);
            CollectionReference collectionReference = db.collection(EQUIPMENT_COLLECTION);
            collectionReference.add(equipment).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(TAG, "onSuccess: ");
                    documentReference.update("docId",documentReference.getId());
                    Toast.makeText(SellEquipmentActivity.this, "Submitted", Toast.LENGTH_SHORT).show();
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
    protected int getLayout(){ return R.layout.activity_seel_equipment;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
