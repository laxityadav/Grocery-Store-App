package com.example.grocerystore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.grocerystore.Modals.GroceryItem;

import java.util.ArrayList;

public class ShowItemsByCategoryActivity extends AppCompatActivity {
    private TextView txtName;
    private RecyclerView recyclerView;

    private GroceryItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items_by_category);

        initViews();

        adapter = new GroceryItemAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        try {
            Intent intent = getIntent();
            String category = intent.getStringExtra("category");
            Utils utils = new Utils(this);
            ArrayList<GroceryItem> items = utils.getItemsByCategory(category);
            adapter.setItem(items);
            txtName.setText(category);
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void initViews() {
        txtName = (TextView) findViewById(R.id.txtCategory);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }
}
