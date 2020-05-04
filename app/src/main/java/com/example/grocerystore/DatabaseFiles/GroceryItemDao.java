package com.example.grocerystore.DatabaseFiles;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.grocerystore.Modals.GroceryItem;

import java.util.List;

@Dao
public interface GroceryItemDao {

    @Insert
    void insert (GroceryItem item);

    @Query("SELECT * FROM grocery_items")
    List<GroceryItem> getAllGroceryItems();

    @Update
    void update(GroceryItem item);

    @Query("SELECT * FROM grocery_items WHERE name LIKE :name")
    List<GroceryItem> searchForItem(String name);

    @Query("SELECT category FROM grocery_items LIMIT 3")
    List<String> getThreeCategories ();

    @Query("SELECT category FROM grocery_items")
    List<String> getAllCategories ();

    @Query("SELECT * FROM grocery_items WHERE category = :category")
    List<GroceryItem> getItemsByCategory(String category);

    @Query("SELECT * FROM grocery_items WHERE _id IN (:ids)")
    List<GroceryItem> getItemsById(int [] ids);
}
