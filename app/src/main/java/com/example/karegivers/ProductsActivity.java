package com.example.karegivers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductsAdapter adapter;
    private List<product> productList;
    private ProgressBar progressBar;
    String tapDescription;

  private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        progressBar=findViewById(R.id.progressbarreading);

        recyclerView=findViewById(R.id.recyclerview_products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList=new ArrayList<>();
        adapter= new ProductsAdapter(productList,this  );

        recyclerView.setAdapter(adapter);
        Bundle b = getIntent().getExtras();
        int value = -1; // or other values
        if(b != null)
            value = b.getInt("key");

        switch(value)
        {
            case 0:tapDescription="Electrician";
            break;
            case 1:tapDescription="Grocery Store";
            break;
            case 2:tapDescription="Laptop Repairing";
            break;
            case 3:tapDescription="Mobile Repairing";
            break;
            case 4:tapDescription="Pest Control";
        }

        db=FirebaseFirestore.getInstance();
       db.collection("products").get()
               .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                   @Override
                   public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                       progressBar.setVisibility(View.GONE);
                       if(!queryDocumentSnapshots.isEmpty())
                       {
                           List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                           for(DocumentSnapshot d: list)
                           {
                                product p=d.toObject(product.class);
                                String des=p.getDescription();
                                if(des.equals(tapDescription))
                                productList.add(p);
                           }
                           adapter.notifyDataSetChanged();
                       }
                   }
               });
    }
}
