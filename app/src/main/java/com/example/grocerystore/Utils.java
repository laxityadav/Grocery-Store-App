package com.example.grocerystore;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.grocerystore.DatabaseFiles.ShopDatabase;
import com.example.grocerystore.Modals.GroceryItem;
import com.example.grocerystore.Modals.Review;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

    private static final String TAG = "Utils";

    public static final String DATABASE_NAME = "fake_database";

    private static int ORDER_ID = 0;
    private static ShopDatabase database;

    private Context context;

    public Utils(Context context) {
        this.context = context;
        database = ShopDatabase.getInstance(context);
    }

    public static int getOrderId () {
        ORDER_ID++;
        return ORDER_ID;
    }

    public void addItemToCart(int id) {
        database.cartItemDao().insert(id);
    }

    public void updateTheRate(GroceryItem item, int newRate) {

        item.setRate(newRate);
        database.groceryItemDao().update(item);
    }

    public boolean addReview(Review review) {
        database.reviewDao().insert(review);
        return true;
    }

    public ArrayList<Review> getReviewForItem(int id) {
        ArrayList<Review> reviews = (ArrayList<Review>) database.reviewDao().getReviewForItemById(id);
        return reviews;
    }

    public ArrayList<GroceryItem> getAllItems() {
        ArrayList<GroceryItem> items = (ArrayList<GroceryItem>) database.groceryItemDao().getAllGroceryItems();
        return items;
    }

    public ArrayList<GroceryItem> searchForItem(String text) {
        ArrayList<GroceryItem> items = (ArrayList<GroceryItem>) database.groceryItemDao().searchForItem(text);
        return items;
    }

    public ArrayList<String> getThreeCategories() {
        ArrayList<GroceryItem> allItems = (ArrayList<GroceryItem>) database.groceryItemDao().getAllGroceryItems();
        ArrayList<String> categories = new ArrayList<>();

        if (null != allItems) {
            for (int i = 0; i < allItems.size(); i++) {
                if (categories.size() < 3) {
                    boolean doesExist = false;
                    for (String s : categories) {
                        if (allItems.get(i).getCategory().equals(s)) {
                            doesExist = true;
                        }
                    }

                    if (!doesExist) {
                        categories.add(allItems.get(i).getCategory());
                    }
                }
            }
        }

        return categories;
    }

    public ArrayList<String> getAllCategories() {
        ArrayList<GroceryItem> allItems = (ArrayList<GroceryItem>) database.groceryItemDao().getAllGroceryItems();
        ArrayList<String> categories = new ArrayList<>();

        if (null != allItems) {
            for (int i = 0; i < allItems.size(); i++) {

                boolean doesExist = false;
                for (String s : categories) {
                    if (allItems.get(i).getCategory().equals(s)) {
                        doesExist = true;
                    }
                }

                if (!doesExist) {
                    categories.add(allItems.get(i).getCategory());
                }

            }
        }
//
        return categories;
    }

    public ArrayList<GroceryItem> getItemsByCategory (String category) {

        ArrayList<GroceryItem> items = (ArrayList<GroceryItem>) database.groceryItemDao().getItemsByCategory(category);
        return items;
    }

    public ArrayList<Integer> getCartItems () {
        int[] items =  database.cartItemDao().getAllItems();

        ArrayList<Integer> allItems = new ArrayList<>();
        for (int i=0; i<items.length; i++) {
            allItems.add(items[i]);
        }
        return allItems;
    }

    public ArrayList<GroceryItem> getItemsById (ArrayList<Integer> ids) {
        int[] itemIds = new int[ids.size()];
        for (int i=0; i<ids.size(); i++) {
            itemIds[i] = ids.get(i);
        }

        ArrayList<GroceryItem> items = (ArrayList<GroceryItem>) database.groceryItemDao().getItemsById(itemIds);
        return items;
    }

    public ArrayList<Integer> deleteCartItem (GroceryItem item) {

        database.cartItemDao().deleteById(item.getId());
        int[] items =  database.cartItemDao().getAllItems();

        ArrayList<Integer> allItems = new ArrayList<>();
        for (int i=0; i<items.length; i++) {
            allItems.add(items[i]);
        }

        return allItems;
    }

    public void removeCartItems () {
        database.cartItemDao().deleteAllItems();
    }

    public void addPopularityPoint (ArrayList<Integer> items) {
        ArrayList<GroceryItem> allItems = (ArrayList<GroceryItem>) database.groceryItemDao().getAllGroceryItems();

        ArrayList<GroceryItem> newItems = new ArrayList<>();
        for (GroceryItem i: allItems) {
            boolean doesExist = false;
            for (int j: items) {
                if (i.getId() == j) {
                    doesExist = true;
                }
            }
            if (doesExist) {
                i.setPopularityPoint(i.getPopularityPoint()+1);
            }
            newItems.add(i);
        }
        for (GroceryItem i: newItems) {
            database.groceryItemDao().update(i);
        }
    }

    public void increaseUserPoint (GroceryItem item, int points) {
        item.setUserPoint(item.getUserPoint()+points);
        database.groceryItemDao().update(item);
    }

}
