package com.example.grocerystore.DatabaseFiles;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface CartItemDao {

    @Query("INSERT INTO cart_items (grocery_item_id) VALUES (:id)")
    void insert (int id);

    @Query("SELECT grocery_item_id FROM cart_items")
    int[] getAllItems();

    @Query("DELETE FROM cart_items WHERE grocery_item_id=:id")
    void deleteById(int id);

    @Query("DELETE FROM cart_items")
    void deleteAllItems();
}
