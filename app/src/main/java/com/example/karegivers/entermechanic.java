package com.example.karegivers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class entermechanic extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText editMechanicname, editMechanicadd, editMechanicmobile, editDescription;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entermechanic);

        db=FirebaseFirestore.getInstance();

        Spinner spinner=findViewById((R.id.descriptionspinner));
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.Description,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        editMechanicadd=findViewById(R.id.mechanicaddress);
        editMechanicmobile=findViewById(R.id.mechanicmobile);
        editMechanicname=findViewById(R.id.mechanicName);
        editDescription=findViewById(R.id.mechanicdescription);

        findViewById(R.id.savingbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=editMechanicname.getText().toString().trim();
                String mobile=editMechanicmobile.getText().toString().trim();
                String desc=editDescription.getText().toString().trim();
                String add=editMechanicadd.getText().toString().trim();

   if(!hasvalidateInputs(name,add,desc,mobile))
   {
       CollectionReference dbProducts =db.collection("products");

       product product = new product(name,add,desc,mobile);
       dbProducts.add(product)
               .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                   @Override
                   public void onSuccess(DocumentReference documentReference) {
                       Toast.makeText(entermechanic.this,"Contact Added",Toast.LENGTH_SHORT).show();
                   }
               })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(entermechanic.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                   }
               });

   }
            }
        });
    }

    private boolean hasvalidateInputs(String name, String address, String desc, String mobile) {
        if (name.isEmpty()) {
            editMechanicname.setError("Name required");
            editMechanicname.requestFocus();
            return true;
        }

        if (address.isEmpty()) {
            editMechanicadd.setError("Address required");
            editMechanicadd.requestFocus();
            return true;
        }

        if (desc.isEmpty()) {
            editDescription.setError("Description required");
            editDescription.requestFocus();
            return true;
        }

        if (mobile.isEmpty()) {
            editMechanicmobile.setError("Mobile no. required");
            editMechanicmobile.requestFocus();
            return true;
        }

        return false;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text=adapterView.getItemAtPosition(i).toString();
        editDescription.setText(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
