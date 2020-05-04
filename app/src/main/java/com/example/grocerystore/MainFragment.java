package com.example.grocerystore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerystore.Modals.GroceryItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private RecyclerView newItemRecView, popularRecView, suggestedRecView;
    private Utils utils;
    private GroceryItemAdapter newItemAdapter, popularItemAdapter, suggestedItemAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        initViews(view);
        initBottomNav();
        utils = new Utils(getActivity());
        initRecViews();

        return view;
    }

    private void initRecViews() {
        newItemAdapter = new GroceryItemAdapter(getActivity());
        suggestedItemAdapter = new GroceryItemAdapter(getActivity());
        popularItemAdapter = new GroceryItemAdapter(getActivity());

        newItemRecView.setAdapter(newItemAdapter);
        suggestedRecView.setAdapter(suggestedItemAdapter);
        popularRecView.setAdapter(popularItemAdapter);

        newItemRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        suggestedRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        updateRecView();
    }

    private void updateRecView() {
        ArrayList<GroceryItem> newItems = utils.getAllItems();

        Comparator<GroceryItem> newItemsComparator = new Comparator<GroceryItem>() {
            @Override
            public int compare(GroceryItem o1, GroceryItem o2) {
                return o1.getId() - o2.getId();
            }
        };

        Comparator<GroceryItem> reversedNewItemsComparator = Collections.reverseOrder(newItemsComparator);
        Collections.sort(newItems, reversedNewItemsComparator);

        if (null != newItems) {
            newItemAdapter.setItem(newItems);
        }

        ArrayList<GroceryItem> popularItems = utils.getAllItems();

        Comparator<GroceryItem> popularityComparator = new Comparator<GroceryItem>() {
            @Override
            public int compare(GroceryItem o1, GroceryItem o2) {
                return o1.getPopularityPoint() - o2.getPopularityPoint();
            }
        };

        Comparator<GroceryItem> reversePopularityComparator = Collections.reverseOrder(popularityComparator);
        Collections.sort(popularItems, reversePopularityComparator);

        popularItemAdapter.setItem(popularItems);

        ArrayList<GroceryItem> suggestedItems = utils.getAllItems();
        Comparator<GroceryItem> suggestedItemsComparator = new Comparator<GroceryItem>() {
            @Override
            public int compare(GroceryItem o1, GroceryItem o2) {
                return o1.getUserPoint() - o2.getUserPoint();
            }
        };

        Comparator<GroceryItem> reveredSuggestedItemsComparator = Collections.reverseOrder(suggestedItemsComparator);
        Collections.sort(suggestedItems, reveredSuggestedItemsComparator);

        suggestedItemAdapter.setItem(suggestedItems);
    }

    private void initBottomNav() {
        bottomNavigationView.setSelectedItemId(R.id.homeActivity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.search:
                        Toast.makeText(getActivity(), "search selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.homeActivity:
                        break;
                    case R.id.cart:
                        break;
                }
                return true;
            }
        });
    }

    private void initViews(View view) {
        bottomNavigationView = view.findViewById(R.id.bottom_nav);
        newItemRecView = view.findViewById(R.id.newItemRecView);
        suggestedRecView = view.findViewById(R.id.suggestedItemRecView);
        popularRecView = view.findViewById(R.id.popularItemRecView);
    }
}
