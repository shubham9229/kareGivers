package com.example.karegivers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class chooseService extends AppCompatActivity {

    String[] listviewTitle = new String[]{
            "Electrician", "Grocery Store","Laptop Repairing", "Mobile Repairing","Pest Control",
    };


    int[] listviewImage = new int[]{
            R.drawable.electrician,R.drawable.grocerystore,R.drawable.laptop,R.drawable.mobile,R.drawable.pest
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_service);

        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 5; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle[i]);
            hm.put("listview_image", Integer.toString(listviewImage[i]));
            aList.add(hm);
        }

        String[] from = {"listview_image", "listview_title"};
        int[] to = {R.id.listview_image, R.id.listview_item_title};

        final SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listviewedit, from, to);
        ListView androidListView = (ListView) findViewById(R.id.chooseservicelist);
        androidListView.setAdapter(simpleAdapter);
        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> list, View v, int pos, long id) {
                Intent intent = new Intent(chooseService.this, ProductsActivity.class);
                Bundle b = new Bundle();
                switch (pos) {
                    case 0:
                        b.putInt("key", 0); //Your id
                        intent.putExtras(b); //Put your id to your next Intent
                        startActivity(intent);

                    break;
                    case 1:
                        b.putInt("key", 1); //Your id
                        intent.putExtras(b); //Put your id to your next Intent
                        startActivity(intent);

                        break;
                    case 2:
                        b.putInt("key", 2); //Your id
                        intent.putExtras(b); //Put your id to your next Intent
                        startActivity(intent);

                        break;
                    case 3:
                        b.putInt("key", 3); //Your id
                        intent.putExtras(b); //Put your id to your next Intent
                        startActivity(intent);

                        break;
                    case 4:
                        b.putInt("key", 4); //Your id
                        intent.putExtras(b); //Put your id to your next Intent
                        startActivity(intent);
                        break;
                    case 5:
                        b.putInt("key", 5); //Your id
                        intent.putExtras(b); //Put your id to your next Intent
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
